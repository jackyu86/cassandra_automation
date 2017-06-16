#export JAVA_HOME=/export/servers/jdk1.7.0_71/
#cd /export/App/cassandra-automation/repo/workspaces/prod
cd /Users/bean/codes/jd/cassandra-automation/repo/workspaces/prod
../../dep/groovy-2.4.6/bin/groovy -cp "../../dep/lib/*" $1 $2 $3 $4
