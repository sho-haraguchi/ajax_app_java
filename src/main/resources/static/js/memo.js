// 「post」という名前の命令（関数）を定義します
function post (){
  // 【捕まえる】HTML内の「投稿する」ボタン（id="submit"）を探して変数に入れます
  const submit = document.getElementById("submit");

  // 【待ち構える】ボタンが「クリック」された時の動きを予約します
  // (e) の「e」はイベントオブジェクト（クリックされた瞬間の情報が入った箱）です
  submit.addEventListener("click", (e) => {

    // 【重要：阻止する】e.preventDefault();
    // ブラウザが元々持っている「送信ボタンを押したらページを再読み込みする」という
    // 標準機能を強制的に停止させます。これがないとAjax（裏側通信）ができません。
    e.preventDefault();

    // 【捕まえる】HTMLの「フォーム全体（id="form"）」を取得します
    const form = document.getElementById("form");

    // 【パッキング】new FormData(form);
    // フォームに入力された値を、Java側が受け取りやすい形式に一括でまとめてくれる便利な道具です。
    // さきほどの「注文用紙（form）」を封筒にパッキングするイメージです。
    const formData = new FormData(form);

    // 【通信機の作成】new XMLHttpRequest();
    // ブラウザが裏側で通信するための「非同期通信専用の郵便屋さん（オブジェクト）」を生成します。
    // ※略して XHR と呼ぶのが一般的です。
    const XHR = new XMLHttpRequest();

    // 【宛先設定】XHR.open(メソッド, パス, 非同期かどうか);
    // 郵便屋さんに「POSTというルールで」「/posts という住所へ」「非同期（true）で」
    // 行くように指示を出し、通信の口を開きます。
    XHR.open("POST", "/posts", true);

    // 【返信の型指定】XHR.responseType = "json";
    // サーバー（Java）から返ってくる返事の形式を「JSON（データの塊）」に指定します。
    XHR.responseType = "json";

    // 【出発！】XHR.send(formData);
    // パッキングしたデータ（formData）を郵便屋さんに持たせて、サーバーへ向かわせます。
    XHR.send(formData);
  });
};

// 【仕上げ】ページが全部読み込まれてから、上の post 関数を実行せよという予約です
window.addEventListener('load', post);

//このjsでやりたいことは「データを送信するときのリロードを防ぐ」というのが主目的。
// sbumit...やsendなど送信系の処理しか書かれていないのはそういうこと。
// もし、レイアウト関係のjsファイルを書きたいなら別で定義する。
// そしてリロードせずに通信するにはXHRを使う必要があるので、それ関係の処理をたくさん書く必要が生じた。
// そして、送信機能のリロードを防ぐためにpreventDefaultも用いた。このjsはXHRとpreventDefaultの役割を把握しておくことが大切。