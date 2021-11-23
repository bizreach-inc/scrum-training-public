# scrum-training
スクラム研修用snsppoiアプリケーションの開発環境周りについて

## required
※最低限開発に必要なもの  
```
Java8  
maven3  
Docker  
Docker Compose  
IntelliJ
```  

Dockerで起動するだけならDocker,Docker Composeだけで問題ありません。
各種必要なものは、現行の公式ドキュメントを参照してください。

## 開発環境準備
IntelliJを前提とする  
1. `$ git clone git@github.com:bizreach/scrum-training.git`でリポジトリをclone
1. IntelliJ起動画面で、Import Projectを選択する
1. cloneしたリポジトリのrootでpom.xmlを選択する
1. `Import Maven project automatically` と `Automatically download`にチェックをいれる
1. `Next`を選択
1. `com.scrum.snsppoi:0.0.1-SNAPSHOT` がデフォルトで選択されているのでそのまま `Next` を選択
1. JDKは1.8を選び`Next` を選択(なければJavaをインストールしておいてください)
1. `Project name` `Project file location` はデフォルトのまま `Finish`  

### CucumberTestを実行する準備  
- このプロジェクトではCucumberTestでシナリオのテストを実行しています
- Chrome + Selenium + CucumberでのE2Eテストとして実行

1. Chromeを入れていない場合はダウンロード
1. ChromeDriverをダウンロード、自分が使っているバージョンに合わせること(最新化されているなら最新のもので大丈夫かと)  
https://sites.google.com/a/chromium.org/chromedriver/downloads
1. DriverのZipを解凍し、`/usr/local/bin/` に移動させる  
`mv ~/driver_dir/chromedriver /usr/local/bin/`

## ローカルでのテストの実行方法  
プロジェクトホームで `$ sh ./test.sh` を実行する  

※テストとApplicationでは使用しているDBが異なるので注意して実行してください  
- テスト時:H2  
- アプリケーション:MySQL  

## ローカルでのアプリ起動手順  

### Dockerで起動
1. プロジェクトのroot(厳密にはdocker-compose.ymlのある階層)に移動する  
ex.) `$ cd ~/git/scrum-training`  

1. docker-composeでアプリケーションとMySQLを起動する  
`$ docker-compose up -d --build`  
設定ファイルなどがうまく反映されない場合は以下オプションを追記。  
` --no-cache`  
**Dockerfileを修正した場合には上記コマンドを実行すること**  

Dockerで起動した際には  
`http://localhost:9000`で起動する  
**初回起動時はプロジェクトrootで以下を実行する**  
`$ sh ./test.sh`  
`$ sh ./init-db.sh`  

### DBの初期化
以下を実行する
`$ sh ./init-db.sh`  

### IntelliJで起動
**dockerでDBが立ち上がっていることが前提**  
`$ docker ps` で、`db` が起動していることを確認します。  
起動していない場合は`$ docker-compose up -d --build`で起動させてください。  
STATUSがExitedになっていおらず、PORTSが `0.0.0.0:3306->3306/tcp, 33060/tcp`になっていればOKです。  
IntelliJの `Debug Application` 、または `Run Application` を実行します。  
IntelliJで起動した際には  
`http://localhost:8080`で起動します。  

## データマイグレーションについて  
- マイグレーションツールにはflywayを使用している
- flywayがおかしくなった場合にはこの項目を参照のこと
- データベース変更のマイグレーションの実行状態をデータベースで保持するので、既存のSQLファイルはいじらないこと
- データベース変更時は以下ディレクトリにSQLファイルを追加する  
`/scrum-training/src/main/resources/db/migration`  
- 命名規則があるのでそこに準じて変更をいれること  
ex. ) `db/migration/V1.2__add-column-image-table.sql`  
たとえば現在が `V1.2__hoge.sql` であれば、次は `V1.3__gehe.sql` にする  
- DB変更SQLを追加したら、まずは `sh ./test.sh` を実行する  
- 失敗したら成功するまで修正して再度叩く  
- 成功したら `sh ./migrate.sh` を実行する  
- バージョンの過去改変を行うとビルド環境への適用に影響するので、必ず積み上げでDBの変更を入れる


## ローカル起動時のアプリケーション(実行中のコンテナ)への入り方
プロジェクトのrootに移動  
`$ cd ~/git/scrum-training`  
起動しているアプリを確認する  
NAMESかCONTAINER IDを確認  
`$ docker ps`  
NAMESまたはCONTAINER IDを指定して以下コマンドを実行する  
`$ docker exec -it scrum-training_app_1 /bin/bash`  
`$ docker exec -it b54 /bin/bash`  

## ローカルでアプリが動いていない場合は？  

コンテナのプロセスやログを確認してデバッグします  
`$ docker-compose logs`  

個別のserviceごとに見たいときは以下で確認できます  
`$ docker-compose logs app`  
`$ docker-compose logs db`  

curlなどでアプリが応答するか確認します  
`curl http://localhost:9000`  

## Appendix : 公式ドキュメントを参照しておくと良いもの
- データベース
  - MySQL
  - H2DB
  - Flyway
    - DB変更(マイグレーション)用
- テスト関連    
  - JUnit
  - Cucumber

