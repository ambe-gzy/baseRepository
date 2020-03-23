package cn.zhenye.common.tbad.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TbkFavoritesResponse implements TbkBaseResponse<List<TbkFavoritesResponse.ChildTbkFavorites>> {

    @SerializedName("tbk_uatm_favorites_get_response")
    public ChildTbResult response;

    @Override
    public List<ChildTbkFavorites> getResult() {
        if (response == null || response.results == null) {
            return null;
        }

        return response.results.favorites;
    }

    public class ChildTbResult {

        @SerializedName("results")
        public ChildFavoritesResult results;
    }

    public class ChildFavoritesResult {
        @SerializedName("tbk_favorites")
        public List<ChildTbkFavorites> favorites;
    }

    public class ChildTbkFavorites {

        @SerializedName("type")
        public int type;

        @SerializedName("favorites_id")
        public int favoritesId;

        @SerializedName("favorites_title")
        public String favoritesTitle;
    }
}
