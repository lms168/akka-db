package example.bean

class KeyValue {
    
    var key: String = ""

    var value: Long = 0

    var topic: String = ""

    var format: String = ""

    var data: Map[String,String] = Map()

    def this(key: String, value: Long) {
        this()
        this.key = key
        this.value = value
    }

    def reset: Map[String,String] = {
         if (key.length > 0) {
            val ss = key.split(":")
            val kk = ss(0).split('\001')
            val vv = ss(1).split('\001')
            data = kk.zip(vv).toMap
         }
        data
    }

}