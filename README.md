# Adx Etl

## APIs

### HTTP

config hosts and port in `core/src/main/resources/application.conf`:

```config
http {
  host = "0.0.0.0"
  port = 2233
}
```

- `GET /` returns "it works."
- `GET /metrics` returns metrics

## Getting started

### Run in local as a developer

config:

core/src/main/resources/application.conf:

```config
kafka {
  topic-in = "p"  # will consume json events here
  topic-out = "q" # will produce json events here
  commit {
    batch-size = 100000
    batch-time-window = 5.seconds
  }
}
```

```config
akka.kafka.producer {
  # Tuning parameter of how many sends that can run in parallel.
  parallelism = 10000
  kafka-clients {
      bootstrap.servers = "127.0.0.1:9092" # kafka servers
      acks = "all"
      retries = 5
      batch.size = 16384
      linger.ms = 1
      buffer.memory = 33554432
  }
}

akka.kafka.consumer {
  kafka-clients {
     bootstrap.servers = "127.0.0.1:9092" # kafka servers
      group.id = "g1" # very very important      
      client.id = "client-1"
      enable.auto.commit = false
      auto.offset.reset = "earliest"
  }
}
```

```bash
$ cd adx-etl
$ sbt run
```

### Build 

```bash
$ cd adx-etl
$ sbt core/universal:packageXzTarball  # build core/target/universal/adx-etl-core.txz
$ sbt core/universal:packageZipTarball # build core/target/universal/adx-etl-core.tgz
$ sbt package                          # same as `sbt core/universal:packageZipTarball`
```

### Run in production

```bash
$ tar xvf adx-etl-core.tgz
$ ./adx-etl-core/bin/adx-etl-core # 默认 HTTP 端口 3344
$ ./adx-etl-core/bin/adx-etl-core -Dhttp.port=8080 # 指定 HTTP 端口为 8080
$ ./adx-etl-core/bin/adx-etl-core -Dconfig.file=/home/xxx.conf # 指定 xxx.conf 作为配置文件
$ JAVA_OPTS="-Xmx4G -Xms1G" ./adx-etl-core/bin/adx-etl-core # 指定 JVM 参数 -Xmx, -Xms 
```

## Start Event Simulator

`core/src/main/resources/application.conf`:

```config
kafka {
  topic-in = "p" # will producer events here
  topic-out = "q"
  commit {
    batch-size = 100000
    batch-time-window = 5.seconds
  }
}
```

```bash
$ sbt 'testOnly *.EventSimulatorStreamSpec' # just for dev
```

```bash
$ cd adx-etl
$ sbt clean stage
$ ./bin/run-simulator.sh
```

## Unit Test

```bash
$ sbt test
```

or explicitly run:

```bash
$ sbt test/test
```
