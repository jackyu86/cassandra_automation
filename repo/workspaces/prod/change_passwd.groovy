import com.jd.jg.admin.cmd.ScpAndTarCmd;
import com.jd.jg.admin.task.*;
import com.jd.jg.admin.ssh.SshFactory;

def host = args[0]

SystemTasks st = new SystemTasks()
st.changePassword(host,'admin','9g9B8@48J25j','vM899&a97q0g')


System.exit(0)
