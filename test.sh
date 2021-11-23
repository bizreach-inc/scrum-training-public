#!/bin/sh

mvn -Dflyway.url=jdbc:h2:./h2db/snsppoi?MODE=MYSQL -Dflyway.user=snsppoi_user -Dflyway.password=snsppoi_pass flyway:clean

if [ $? -gt 0 ]; then
    echo "\033[0;31m !! fail !! \033[0;39m"
    exit
fi

mvn -Dflyway.url=jdbc:h2:./h2db/snsppoi?MODE=MYSQL -Dflyway.user=snsppoi_user -Dflyway.password=snsppoi_pass flyway:baseline

if [ $? -gt 0 ]; then
    echo "\033[0;31m !! fail !! \033[0;39m"
    exit 1
fi

mvn -Dflyway.url=jdbc:h2:./h2db/snsppoi?MODE=MYSQL -Dflyway.user=snsppoi_user -Dflyway.password=snsppoi_pass flyway:migrate

if [ $? -gt 0 ]; then
    mvn -Dflyway.url=jdbc:h2:./h2db/snsppoi?MODE=MYSQL -Dflyway.user=snsppoi_user -Dflyway.password=snsppoi_pass flyway:repair
    echo "\033[0;31m !! fail fix your SQL !! \033[0;39m"
    exit 1
fi

if [ $(find /usr/local/bin -name "chromedriver") == "/usr/local/binuchromedriver" ] ; then
echo "\033[0;31m !! you need chromedriver for cucumber test !! http://chromedriver.chromium.org/downloads \033[0;39m"
fi
mvn -Dflyway.url=jdbc:h2:./h2db/snsppoi?MODE=MYSQL -Dflyway.user=snsppoi_user -Dflyway.password=snsppoi_pass test

if [ $? -gt 0 ]; then
    echo "\033[0;31m !! fail !! \033[0;39m"
    exit 1
fi
