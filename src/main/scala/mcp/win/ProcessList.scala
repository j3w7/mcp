package mcp.win;
/*
package processes.win

import com.sun.jna.Native;
import com.sun.jna.platform.win32.*;
import com.sun.jna.win32.W32APIOptions;

class ProcessList {

  def main(args: Array[String]) {
        val winNT = (WinNT) Native.loadLibrary(WinNT.class, W32APIOptions.UNICODE_OPTIONS);

        val snapshot = winNT.CreateToolhelp32Snapshot(Tlhelp32.TH32CS_SNAPPROCESS, new WinDef.DWORD(0));

        Tlhelp32.PROCESSENTRY32.ByReference processEntry = new Tlhelp32.PROCESSENTRY32.ByReference();

        while (winNT.Process32Next(snapshot, processEntry)) {
            System.out.println(processEntry.th32ProcessID + "\t" + Native.toString(processEntry.szExeFile));
        }

        winNT.CloseHandle(snapshot);
    }
}

 */
