# Русский свет. Тестовое задание
Описание задания смотри [здесь](task.txt)

Чтобы запустить проект, необходимо создать базу данных "RussianLight" и изменить настройки в application.properties в папке resources.

### Функционал:

Регистрация. ADMIN имеет доступ ко всему. USER имеет доступ к просмотру продуктов/категорий и поиску.

![ScreenShot](images/signUp.JPG)

Аутентификация. В заголовке ответа необходимо скопировать токен

![ScreenShot](images/login.JPG)

Вставляем скопируемый токен в заголовок Authorization

![ScreenShot](images/3.JPG)

Список всех продуктов

![ScreenShot](images/findAll.JPG)

Список всех категорий

![ScreenShot](images/allCat.JPG)

Поиск продуктов по категории

![ScreenShot](images/findByCatId.JPG)

Поиск продуктов по имени

![ScreenShot](images/findByProductName.JPG)

Поиск продуктов по диапазону цен

![ScreenShot](images/findByPriceRange.JPG)

Удалить категорию. Все продукты без категории имеют статус НЕАКТИВЕН

![ScreenShot](images/deleteCat.JPG)


![ScreenShot](images/deletedCatInProduct.JPG)

Нельзя присвоить продукту несуществующую категорию

![ScreenShot](images/cantSetNullCategory.JPG)

При назначении категории продукту его статус становится АКТИВЕН

![ScreenShot](images/productAfterPatch.JPG)


