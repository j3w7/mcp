package mcp.win;
/*

package processes.win;

import  enumeration.EnumerateWindows.Kernel32.*;
import  enumeration.EnumerateWindows.Psapi.*;
import  enumeration.EnumerateWindows.User32DLL.*;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.ptr.PointerByReference;

 class EnumerateWindows {
    val MAX_TITLE_LENGTH = 1024;

    def main(args: Array[String]) {
    val buffer = new char[MAX_TITLE_LENGTH * 2];
    GetWindowTextW(GetForegroundWindow(), buffer, MAX_TITLE_LENGTH);
    System.out.println("Active window title: " + Native.toString(buffer));

    val pointer = new PointerByReference();
    GetWindowThreadProcessId(GetForegroundWindow(), pointer);
    val process = OpenProcess(PROCESS_QUERY_INFORMATION | PROCESS_VM_READ, false, pointer.getValue());
    GetModuleBaseNameW(process, null, buffer, MAX_TITLE_LENGTH);
    System.out.println("Active window process: " + Native.toString(buffer));
}

 class Psapi {
     { Native.register("psapi"); }
      native int GetModuleBaseNameW(Pointer hProcess, Pointer hmodule, char[] lpBaseName, int size);
}

 class Kernel32 {
     { Native.register("kernel32"); }
      val PROCESS_QUERY_INFORMATION = 0x0400;
      val PROCESS_VM_READ = 0x0010;
      native int GetLastError();
      native Pointer OpenProcess(int dwDesiredAccess, boolean bInheritHandle, Pointer pointer);
}

 class User32DLL {
     { Native.register("user32"); }
      native int GetWindowThreadProcessId(HWND hWnd, PointerByReference pref);
      native HWND GetForegroundWindow();
      native int GetWindowTextW(HWND hWnd, char[] lpString, int nMaxCount);
}
}

 */
