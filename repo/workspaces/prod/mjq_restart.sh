for i in {155..160}; do echo $i; ./run_groovy.sh danger/restart_node.groovy 172.28.147.$i ; done

for i in {11..29}; do echo $i; ./run_groovy.sh danger/restart_node.groovy 172.28.148.$i ; done
