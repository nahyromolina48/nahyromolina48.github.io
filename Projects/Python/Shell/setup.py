import sys
from cx_Freeze import setup, Executable

include_files = ['setrun.inf']
base = None

if sys.platform == "win32":
    base = "Win32GUI"

setup(name="RAP",
      version="0.1",
      description="Remote Access program",
      options={'build_exe': {'include_files': include_files}},
      executables=[Executable("client.py", base=base)])
