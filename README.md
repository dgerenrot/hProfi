# hProfi 

An HPROF dump parser/analyzer.

As of today, it can load an hprof heap dump file into Mongo database. 
All the Instance Dumps, Object Array Dumps, Primitive Array Dumps, Class Dumps and Load Class tags are loaded.

In-memory store, i.e., without any database, has also been implemented.

The long-term goal is to have better querying opportunities than the ones 
provided by visualvm.

See also https://java.net/downloads/heap-snapshot/hprof-binary-format.html

