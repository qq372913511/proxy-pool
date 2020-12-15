ps ax|grep java | grep -v "jenkins" | grep -v "color" | cut -d '?' -f1 | xargs kill -9
nohup java -jar proxypoolweb/target/proxy-pool-web-0.0.1-SNAPSHOT.jar >> /mnt/raid0_4tb/log/proxy-pool/web/log.log 2>&1 &
nohup java -jar proxypoolcrawler/target/proxyIp-pool-1.0-SNAPSHOT.jar >> /mnt/raid0_4tb/log/proxy-pool/crawler/log.log 2>&1 &