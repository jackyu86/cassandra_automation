import com.jd.jg.admin.cmd.ScpAndTarCmd;
import com.jd.jg.admin.task.*;
import com.jd.jg.admin.ssh.SshFactory;

def host = args[0]

SystemTasks st = new SystemTasks()
st.distributeKey(host,'admin','vM899&a97q0g',"../../install/authorized_keys")


System.exit(0)
