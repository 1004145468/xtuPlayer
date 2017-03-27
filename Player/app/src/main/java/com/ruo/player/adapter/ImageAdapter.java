package com.ruo.player.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruo.player.R;
import com.ruo.player.entries.MovieModel;

import java.util.List;

public class ImageAdapter extends BaseAdapter {

    private LayoutInflater mLayoutinflater;
    private List<MovieModel> mDatas;

    public ImageAdapter(Context context, List<MovieModel> mDatas) {
        mLayoutinflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CoverViewHolder coverViewHolder = null;
        if (convertView == null) {
            convertView = mLayoutinflater.inflate(R.layout.item_launcher, parent, false);
            coverViewHolder = new CoverViewHolder(convertView);
            convertView.setTag(coverViewHolder);
        } else {
            coverViewHolder = (CoverViewHolder) convertView.getTag();
        }
        Bitmap thumbnail = mDatas.get(position).getThumbnail();
        coverViewHolder.coverView.setImageBitmap(thumbnail);
        coverViewHolder.titleView.setText(mDatas.get(position).getMovieName());
        return convertView;
    }


    class CoverViewHolder {

        public ImageView coverView;
        public TextView titleView;

        public CoverViewHolder(View rootView) {
            coverView = (ImageView) rootView.findViewById(R.id.launcher_cover);
            titleView = (TextView) rootView.findViewById(R.id.launcher_title);
        }
    }

    /**
     * 创建带有倒影的图片

     public void createRefectedBitmap() {
     //原图片与倒影图片之间的距离
     int refectionGap = 4;
     //向图片数组中加入图片
     for (int i = 0; i < imageIds.length; i++) {
     int imageId = imageIds[i];
     //原图片
     Bitmap resourceBitmap = BitmapFactory.decodeResource(context.getResources(), imageId);
     int width = resourceBitmap.getWidth();
     int height = resourceBitmap.getHeight();
     //倒影图片
     //reource:原图片
     //x,y:生成倒影图片的起始位置
     //width,heiht:生成倒影图片宽和高
     //Matrix m:用来设置图片的样式(倒影)
     Matrix m = new Matrix();
     //x:水平翻转；y:垂直翻转   1支持； -1翻转
     m.setScale(1, -1);
     Bitmap refrectionBitmap = Bitmap.createBitmap(resourceBitmap, 0, height / 2, width, height / 2, m, false);
     //合成的带有倒影的图片
     Bitmap bitmap = Bitmap.createBitmap(width, height + height / 2, Config.ARGB_8888);
     //创建画布
     Canvas canvas = new Canvas(bitmap);
     //绘制原图片
     canvas.drawBitmap(resourceBitmap, 0, 0, null);
     //绘制原图片与倒影之间的间隔
     Paint defaultPaint = new Paint();
     canvas.drawRect(0, height, width, height + refectionGap, defaultPaint);
     //绘制倒影图片
     canvas.drawBitmap(refrectionBitmap, 0, height + refectionGap, null);

     //ps中的渐变和遮罩效果
     Paint paint = new Paint();
     //设置遮罩效果
     paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
     //设置渐变效果
     //设置着色器为遮罩着色
     LinearGradient shader = new LinearGradient(0, height, 0, bitmap.getHeight(), 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
     paint.setShader(shader);
     canvas.drawRect(0, height, width, bitmap.getHeight(), paint);

     //创建BitmapDrawable图片
     BitmapDrawable bd = new BitmapDrawable(bitmap);
     //消除图片锯齿效果，使图片平滑
     bd.setAntiAlias(true);

     ImageView imageView = new ImageView(context);
     imageView.setImageDrawable(bd);
     //设置图片大小
     imageView.setLayoutParams(new Gallery3D.LayoutParams(160, 240));
     //将图片放置在images数组中
     images[i] = imageView;
     }
     }
     */
}