package in.tech_camp.ajax_app_java.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.tech_camp.ajax_app_java.entity.PostEntity;
import in.tech_camp.ajax_app_java.form.PostForm;
import in.tech_camp.ajax_app_java.repository.PostRepository;
import lombok.AllArgsConstructor;

// 【役割】このクラスは、ユーザーからの「リクエスト」を受け取って、適切な「ページ」や「データ」を返す司令塔です
@Controller
// 【便利機能】これをつけると、下の「private final ...」を自動的に準備（依存性の注入）してくれます
@AllArgsConstructor
public class PostController {

  // 【倉庫番】データベースと直接やり取りをするための「リポジトリ」を準備します
  private final PostRepository postRepository;

  // 【表示】トップページ（/）にアクセスされた時の処理
  // 【表示】トップページ（/）にアクセスされた時の処理
  @GetMapping("/")
  public String showList(Model model) {
    // 1. 倉庫（DB）からすべての投稿データを取ってくる
    var postList = postRepository.findAll();
    // 2. 取ってきたデータを、HTML側で「postList」という名前で使えるようにセットする
    model.addAttribute("postList", postList);
    
    // 💡【ココを追加！】HTMLのフォーム用に、空の「PostForm」の器を渡してあげる
    model.addAttribute("postForm", new PostForm());

    // 3. index.htmlを表示する
    return "posts/index";
  }

  // 【保存】投稿ボタンが押されて「/posts」にデータが送られてきた時の処理
  @PostMapping("/posts")
  public ResponseEntity<PostEntity> savePost(@ModelAttribute("postForm") PostForm form){
    // ①【荷解き】HTMLの th:object="${postForm}" から届いたデータ（form）を受け取る

    // ②【詰め替え】「入力フォーム用の器（PostForm）」から「データベース保存用の器（PostEntity）」へ中身を移す
    PostEntity post = new PostEntity();
    post.setContent(form.getContent()); // formの中身を、postにセットする

    // ③【保管】リポジトリを使って、データベースに実際に保存（インサート）する
    postRepository.insert(post);

    PostEntity resultPost = postRepository.findById(post.getId());

    // ④【移動】保存が終わったら、真っさらな状態でトップページ（/）に戻る（リダイレクト）
    return ResponseEntity.ok(resultPost);
  }
}