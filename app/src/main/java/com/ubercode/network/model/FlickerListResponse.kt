package com.ubercode.network.model

data class FlickerListResponse(var photos: Photos?) {

    /**
     * photos : {"page":1,"pages":7798,"perpage":20,"total":"155949","photo":[{"id":"45892860585","secret":"f9a7c8fdf1","server":"7865","farm":8,"title":"CAT 03 KH0105 01"},{"id":"32932164258","secret":"4c0e55f13c","server":"7839","farm":8,"title":"Cat"}]}
     */

    data class Photos(var page: Int,
                      var pages: Int,
                      var perpage: Int,
                      var total: String?,
                      var photo: List<PhotoListResult>?) {
        /**
         * page : 1
         * pages : 7798
         * perpage : 20
         * total : 155949
         * photo : [{"id":"45892860585","secret":"f9a7c8fdf1","server":"7865","farm":8,"title":"CAT 03 KH0105 01"},{"id":"32932164258","secret":"4c0e55f13c","server":"7839","farm":8,"title":"Cat"}]
         */


        data class PhotoListResult(var id: String?,
                                   var secret: String?,
                                   var server: String?,
                                   var farm: Int,
                                   var title: String?) {
            /**
             * id : 45892860585
             * secret : f9a7c8fdf1
             * server : 7865
             * farm : 8
             * title : CAT 03 KH0105 01
             */
            constructor() : this(null, null, null, 0, null)
        }

        constructor() : this(0, 0, 0, null, null)
    }

    constructor() : this(null)

}
