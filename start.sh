BUILD_IN=dontKillMe
echo Kill All Java Process
ps ax | grep java | grep proxy-pool-web-0.0.1-SNAPSHOT.jar | cut -d '?' -f1 | xargs kill -9 || true
ps ax | grep java | grep proxyIp-pool-1.0-SNAPSHOT.jar | cut -d '?' -f1 | xargs kill -9 || true
echo Start proxy-pool-web
nohup java -jar proxypoolweb/target/proxy-pool-web-0.0.1-SNAPSHOT.jar >> /mnt/raid0_4tb/log/proxy-pool/web/log.log 2>&1 &
echo Start proxy-pool-crawler
nohup java -jar proxypoolcrawler/target/proxyIp-pool-1.0-SNAPSHOT.jar >> /mnt/raid0_4tb/log/proxy-pool/crawler/log.log 2>&1 &
echo Start Finished