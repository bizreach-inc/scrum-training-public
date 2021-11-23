@txn
Feature: 投稿された画像を眺めることができる

  Scenario: トップ画面に画像が表示される
    Given 画像を投稿している
    When トップ画面に遷移する
    Then 投稿した画像が表示されている