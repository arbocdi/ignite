# SQL-client
* Используем DBeaver-ee для доступа к кешу ignite, который поддерживает SQL.
* для работы требуется добавить файлы драйвера:

  1.  All the jars under {apache_ignite_release}\libs directory.
  2.  All the jars under {apache_ignite_release}\ignite-indexing
  3. {apache_ignite_release}\ignite-spring directories.

* URL подключения имеет вид
```
jdbc:ignite:cfg://[<params>@]<config_url>
jdbc:ignite:cfg://cache=testCache@file:///opt/default-config.xml
```
* нужно собрать с правильными зависимостями и запустить ноду с примерам:
```
git clone https://github.com/srecon/ignite-book-code-samples
chapter-installation
mvn clean dependency:copy-dependencies package
+ фиксим зависимости
java -jar ./target/HelloIgniteSpring-runnable.jar
```
