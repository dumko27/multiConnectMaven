# multiConnectMaven
Запустить pgAdmin
Настроить сервер на username - postgres
и password - qwaszx27.

Создать БД entrytest
Скрипт:


Создать таблицу entries:
Скрипт:

Импортировать проект себе в IDE.
Выполнить "Clean and build", чтобы проекту подтянулись нужные ему зависимости.
Запустить Main.java в пакете com.multiconnect.

Java-приложение мониторит заданную директорию multiConnectMaven/src/test/resources/TestXML на наличие файлов определенного XML-форматав  и при обнаружении файла подходящего формата приложение сохраняет его содержимое в аналогичную по структуре таблицу в PostgreSQL и перемещает файл в каталог обработанных файлов (создает новую папку GoodXML в multiConnectMaven/src/test/resources/).

Формат входного XML-файла:
<Entry>
  <!--строка длиной до 1024 символов-->
  <content>Содержимое записи</content>
  <!--дата создания записи-->
  <date>2014-01-01 00:00:00</date>
</Entry>

В папке TestXML уже описанные примеры xml, которые нужно отмониторить.
Подходят под условие testEntry.xml и insertedTestEntry.xml (который в вложенной папке SubFolder).

Если посмотреть в логи (создатся файл multiConnect.log в корневой папке проекта), то увидим под "workWithSQL entries:", что находиится теперь в БД
