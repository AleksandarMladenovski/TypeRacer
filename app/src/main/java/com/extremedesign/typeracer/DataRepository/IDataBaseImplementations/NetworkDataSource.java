package com.extremedesign.typeracer.DataRepository.IDataBaseImplementations;


public class NetworkDataSource  // implements IDataSource
 {
//    private static GetRetrofitDataService dataServicee;
//
//    public NetworkDataSource(Retrofit retrofit) {
//        if (dataServicee == null) {
//            dataServicee = retrofit.create(GetRetrofitDataService.class);
//        }
//
//    }
//
//    @Override
//    public void getData(String amount, String category, String difficulty, String type,final CustomListenerRep listenerRep) {
//
//
//        Call<OpenTDBResult> call = dataServicee.get_Trivia_Questions(amount, category, difficulty, type);
//        call.enqueue(new Callback<OpenTDBResult>() {
//            @Override
//            public void onResponse(Call<OpenTDBResult> call, Response<OpenTDBResult> response) {
//                listenerRep.RepReturn(response.body().getQuestions());
//            }
//
//            @Override
//            public void onFailure(Call<OpenTDBResult> call, Throwable t) {
//                listenerRep.RepReturn(new ArrayList<Question>());
//            }
//        });
//
//    }
//    @Override
//    public void insertData(List<Question> questionList) {
//        //TODO MAYBE POST METHOD
//    }
//
//    @Override
//    public void getListOfCategories(CategoryListener listener) {
//
//    }
}
