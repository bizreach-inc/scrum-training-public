Feature: 画像を投稿できる
  # Enter feature description here

  Scenario: 画像を投稿できる
    Given 画像をフォームで選択する
    When アップロードボタンを押す
    Then トップに遷移すると投稿した画像が見られる