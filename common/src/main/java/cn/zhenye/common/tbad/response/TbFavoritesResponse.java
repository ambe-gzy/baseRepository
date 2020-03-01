package cn.zhenye.common.tbad.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TbFavoritesResponse {

    @SerializedName("tbk_uatm_favorites_get_response")
    public TbResult response;

    public class TbResult {
        @SerializedName("results")
        public FavoritesResult results;
    }

    public class FavoritesResult {

        @SerializedName("tbk_favorites")
        public List<TbkFavorites> favorites;


    }

    public class TbkFavorites {

        @SerializedName("type")
        public int type;

        @SerializedName("favorites_id")
        public int favoritesId;

        @SerializedName("favorites_title")
        public String favoritesTitle;
    }
}
