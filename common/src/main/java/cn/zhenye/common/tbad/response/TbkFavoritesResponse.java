package cn.zhenye.common.tbad.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TbkFavoritesResponse implements TbkBaseResponse<TbkFavoritesResponse> {

    public ChildTbResult getResponse() {
         return response;
    }

    public void setResponse(ChildTbResult response) {
        this.response = response;
    }

    @SerializedName("tbk_uatm_favorites_get_response")
    private ChildTbResult response;

    @Override
    public TbkFavoritesResponse getResult() {
        return this;
    }

    public class ChildTbResult {
        public ChildFavoritesResult getResults() {
            return results;
        }

        public void setResults(ChildFavoritesResult results) {
            this.results = results;
        }

        @SerializedName("results")
        private ChildFavoritesResult results;
    }

    public class ChildFavoritesResult {

        public List<ChildTbkFavorites> getFavorites() {
            return favorites;
        }

        public void setFavorites(List<ChildTbkFavorites> favorites) {
            this.favorites = favorites;
        }

        @SerializedName("tbk_favorites")
        private List<ChildTbkFavorites> favorites;


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
