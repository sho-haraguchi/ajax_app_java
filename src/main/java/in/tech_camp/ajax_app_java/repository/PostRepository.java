// ① このファイル（インターフェース）がどのフォルダー（パッケージ）に所属しているかを示しています
package in.tech_camp.ajax_app_java.repository;

// ② プログラム内で使う他の部品（クラスやMyBatisの機能）を読み込んでいます（インポート）
import java.util.List; // 複数のデータをまとめて扱うためのリスト機能
import org.apache.ibatis.annotations.Insert; // データを保存するための機能
import org.apache.ibatis.annotations.Mapper; // これがMyBatisのファイルであることを示す機能
import org.apache.ibatis.annotations.Options; // データベース操作の細かいオプションを設定する機能
import org.apache.ibatis.annotations.Select; // データを取得するための機能
import in.tech_camp.ajax_app_java.entity.PostEntity; // データの入れ物となる「PostEntity」というクラス

// ③ MyBatisに対して「これはデータベースを操作するファイルですよ」と教えています
@Mapper
public interface PostRepository {

  // ④ SQL文：postsテーブルから、作成日時（created_at）が新しい順（降順：desc）でデータをすべて取得します
  @Select("select * from posts order by created_at desc")
  // ⑤ ④のSQLを実行し、取得した複数のデータを「PostEntityのリスト」として返すメソッドです
  List<PostEntity> findAll();

  // ⑥ SQL文：postsテーブルの「content」カラムに、送られてきた投稿内容を保存します
  @Insert("insert into posts (content) values (#{content})")
  // ⑦ データベース側で自動で割り振られた「ID（連番）」を、Java側のプログラムでもすぐに使えるように回収する設定です
  @Options(useGeneratedKeys = true, keyProperty = "id")
  // ⑧ ⑥と⑦の処理を実行し、引数で受け取った「post」のデータをデータベースに保存するメソッドです
  void insert(PostEntity post);

  // ⑨ SQL文：postsテーブルから、指定された「id」と一致するデータを1件だけ取得します
  @Select("SELECT * FROM posts WHERE id = #{id}")
  // ⑩ ⑨のSQLを実行し、見つかった1件のデータを「PostEntity」の形で返すメソッドです
  PostEntity findById(int id);
}