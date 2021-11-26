package com.example.sysubbsproject.db;

import com.example.sysubbsproject.db.bmob.Person;
import com.example.sysubbsproject.db.bmob.Post;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

public class PostHandler {
    public static void CreatePost(String title, String content, Person author, List<String> imagePaths, String label, SaveListener<String> listener) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setAuthor(author);
        post.setLabel(label);

        // 上传图片后端接口
        if (imagePaths != null && imagePaths.size() > 0) {
            final String[] paths = imagePaths.toArray(new String[0]);
            BmobFile.uploadBatch(paths, new UploadBatchListener() {
                @Override
                public void onSuccess(List<BmobFile> files, List<String> urls) {
                    post.setImageUrls(urls);
                    post.save(listener);
                }

                @Override
                public void onProgress(int curIndex, int curPercent, int total,int totalPercent) {

                }

                @Override
                public void onError(int statuscode, String errormsg) {
                    listener.done("", new BmobException(statuscode, errormsg));
                }
            });
        }
        else {
            post.save(listener);
        }
    }

    // 帖子获取后端接口
    public static void GetPosts(String label, int limit, int skip, FindListener<Post> listener) {
        BmobQuery<Post> query = new BmobQuery<>();
        if (label != null && !label.equals("")) {
            query.addWhereEqualTo("labels", label);
        }
        query.setLimit(limit).setSkip(skip).order("-createdAt");
        query.findObjects(listener);
    }
}
