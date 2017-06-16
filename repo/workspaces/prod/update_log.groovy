import com.jd.jg.admin.cmd.*


def upload_log(h){
	ScpCmd scp = new ScpCmd()
	scp.localFile = "./template/logback.xml"
	scp.remoteFile = "/export/App/jgcass.jd.local/apache-cassandra-2.2.3/conf/logback.xml"
	scp.execute(h)
}

new File(args[0]).eachLine{
    upload_log(it)
}
System.exit(0)
