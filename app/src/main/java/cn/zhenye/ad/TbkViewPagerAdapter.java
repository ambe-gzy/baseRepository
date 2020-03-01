package cn.zhenye.ad;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import cn.zhenye.ad.vm.TbkVMManager;
import cn.zhenye.common.tbad.response.TbkFavoritesResponse;
import cn.zhenye.home.R;

public class TbkViewPagerAdapter extends RecyclerView.Adapter<TbkViewPagerAdapter.ViewHolder> {
    TbkFavoritesResponse.ChildFavoritesResult mResult;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_tbk_ad,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TbkFavoritesResponse.ChildTbkFavorites favorites = mResult.getFavorites().get(position);
        String title =  favorites.favoritesTitle;
        int favourId =  favorites.favoritesId;
        holder.tittle.setText(title);
        holder.id.setText(String.valueOf(favourId));
        Log.d("tbk","position" + position);
        TbkVMManager.getInstance().loadFavoriteItems(favourId);
    }

    @Override
    public int getItemCount() {
        return mResult!=null?mResult.getFavorites().size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tittle;
        public TextView id;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tittle = itemView.findViewById(R.id.tv_tbk_pager_title);
            id = itemView.findViewById(R.id.tv_tbk_pager_id);
        }
    }

    public void setData(TbkFavoritesResponse response ) {
        mResult = response.getResponse().getResults();
        notifyDataSetChanged();
    }

}
