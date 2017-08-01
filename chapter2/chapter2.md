# Структура ignite
![arcitecture](igniteParts.png)
* все ноды равнозначны, нет никаких мастер-нод
![clientServer](clientServer.png)
# Топология кластера
* Клиентские ноды могут выполнять рассчеты, более того можно создать клстер
в котором серверные ноды будут только хранить данные, а клинентские ноды их обрабатывать.
![clientWork](clientWork.png)
* Ноды ignite могут быть
  1. embedded
  ![embedded](embedded.png)
  2. in separate jvm
  ![separate](separate.png)
# Топология кеша
* Типы топологий
  1. Partitioned (топология по умолчанию) - данные кеша разбиты с избыточностью по нодам кластера.
  ![partitionedCache](partitionedCache.png)
  2. Replicated - все данные хранятся на всех нодах - очень быстрый доступ к данным. Недостаток - каждая
  нова порция данных должна быть скопирована на все ноды - медленные апдейты.
  ![replicated](replicatedCache.png)
