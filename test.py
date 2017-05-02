from kazoo.client import KazooClient

zk = KazooClient(hosts='137.112.89.172:2181')
zk.start()