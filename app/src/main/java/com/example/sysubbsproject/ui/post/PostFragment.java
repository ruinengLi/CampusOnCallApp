package com.example.sysubbsproject.ui.post;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sysubbsproject.R;
import com.example.sysubbsproject.db.PostHandler;
import com.example.sysubbsproject.db.bmob.Person;
import com.example.sysubbsproject.db.bmob.Post;
import com.example.sysubbsproject.ui.post.beans.ImageItem;
import com.example.sysubbsproject.ui.post.utils.Bimp;
import com.example.sysubbsproject.ui.post.utils.BitmapUtils;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static android.app.Activity.RESULT_OK;

public class PostFragment extends Fragment {

    private static final int PICK_PHOTO = 1;
    private List<Bitmap> mResults = new ArrayList<>();

    public static Bitmap bimap;
    private GridView noScrollgridview;
    private PostFragment.GridAdapter adapter;

    ActivityResultLauncher<Intent> activityResultLauncher;

    Button post_button;
    Chip label_1,label_2,label_3,label_4,label_5,label_6;
    EditText title,content;
    Post post;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_navigation_post, container, false);
        bimap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_addpic_focused);
        mResults.add(bimap);

        // adapter settings
        noScrollgridview = (GridView) root.findViewById(R.id.noScrollgridview);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(getContext());
        adapter.update();
        noScrollgridview.setAdapter(adapter);

        List<String> imagesUrls = new ArrayList<String>();

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                            ArrayList<String> resultList = data.getStringArrayListExtra(PhotoPickerActivity.KEY_RESULT);
                            imagesUrls.addAll(resultList);
                            showResult(resultList);
                        }
                    }
                });
        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == mResults.size() - 1 || i == Bimp.tempSelectBitmap.size()) {
                    Intent intent = new Intent(getContext(), PhotoPickerActivity.class);
                    intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, true);
                    intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_SINGLE);
                    intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, PhotoPickerActivity.DEFAULT_NUM);
                    // 总共选择的图片数量
                    intent.putExtra(PhotoPickerActivity.TOTAL_MAX_MUN, Bimp.tempSelectBitmap.size());
                    activityResultLauncher.launch(intent);
                } else {
                    Intent intent = new Intent(getContext(),
                            PreviewPhotoActivity.class);
                    intent.putExtra("position", "1");
                    intent.putExtra("ID", i);
                    startActivity(intent);
                }
            }
        });

        post_button = root.findViewById(R.id.button);
        label_1 = root.findViewById(R.id.messageLabel_1);
        label_2 = root.findViewById(R.id.messageLabel_2);
        label_3 = root.findViewById(R.id.messageLabel_3);
        label_4 = root.findViewById(R.id.messageLabel_4);
        label_5 = root.findViewById(R.id.messageLabel_5);
        label_6 = root.findViewById(R.id.messageLabel_6);
        title = root.findViewById(R.id.post_title);
        content = root.findViewById(R.id.post_text);

        post = new Post();

        label_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post.setLabel("树洞");
            }
        });
        label_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post.setLabel("招募");
            }
        });
        label_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post.setLabel("课程评价");
            }
        });
        label_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post.setLabel("资料分享");
            }
        });
        label_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post.setLabel("闲置");
            }
        });
        label_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post.setLabel("失物招领");
            }
        });

        post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title_text = title.getText().toString().trim();
                String content_text = content.getText().toString().trim();
                String label = post.getLabel();
                Person author = BmobUser.getCurrentUser(Person.class);

                PostHandler.CreatePost(title_text,content_text,author,imagesUrls,label, new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if(e==null){
                            Toast.makeText(getContext(),"发帖成功",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });

        return root;
    }


    public void showResult(ArrayList<String> paths) {
        if (mResults == null) {
            mResults = new ArrayList<>();
        }
        if (paths.size() != 0) {
            mResults.remove(mResults.size() - 1);
        }
        for (int i = 0; i < paths.size(); i++) {
            // 压缩图片
            Bitmap bitmap = BitmapUtils.decodeSampledBitmapFromFd(paths.get(i), 400, 500);
            // 针对小图也可以不压缩
//            Bitmap bitmap = BitmapFactory.decodeFile(paths.get(i));
            mResults.add(bitmap);

            ImageItem takePhoto = new ImageItem();
            takePhoto.setBitmap(bitmap);
            Bimp.tempSelectBitmap.add(takePhoto);
        }
        mResults.add(BitmapFactory.decodeResource(getResources(), R.drawable.icon_addpic_focused));
        adapter.notifyDataSetChanged();
    }


    public class GridAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }
        public void update() {
            loading();
        }
        public int getCount() {
            if (Bimp.tempSelectBitmap.size() == 9) {
                return 9;
            }
            return (Bimp.tempSelectBitmap.size() + 1);
        }
        public Object getItem(int arg0) {
            return mResults.get(arg0);
        }
        public long getItemId(int arg0) {
            return arg0;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            PostFragment.GridAdapter.ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_gridview, null);
                holder = new PostFragment.GridAdapter.ViewHolder();
                holder.image = (ImageView) convertView
                        .findViewById(R.id.imageView1);
                convertView.setTag(holder);
            } else {
                holder = (PostFragment.GridAdapter.ViewHolder) convertView.getTag();
            }

            if (position == Bimp.tempSelectBitmap.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.icon_addpic_focused));
                if (position == 9) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
            }
            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        adapter.notifyDataSetChanged();
                        break;
                }
                super.handleMessage(msg);
            }
        };

        public void loading() {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        if (Bimp.max == Bimp.tempSelectBitmap.size()) {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                            break;
                        } else {
                            Bimp.max += 1;
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        }
                    }
                }
            }).start();
        }
    }

}