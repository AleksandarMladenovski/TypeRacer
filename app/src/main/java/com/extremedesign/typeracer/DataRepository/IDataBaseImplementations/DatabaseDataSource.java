package com.extremedesign.typeracer.DataRepository.IDataBaseImplementations;

import android.content.Context;

import com.extremedesign.typeracer.DataRepository.RoomDatabase.TypeRacerDatabase;
import com.extremedesign.typeracer.listener.JobWorker;
import com.extremedesign.typeracer.listener.ProfileImageListener;
import com.extremedesign.typeracer.model.ProfileImage;
import java.util.List;

public class DatabaseDataSource implements IDataSource {
    private Context context;

    public DatabaseDataSource() {
    }
    public DatabaseDataSource(Context context) {
        this.context=context;
    }


    @Override
    public void insertDataProfileImage(final ProfileImage profileImage, final JobWorker listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                TypeRacerDatabase.getDatabase(context).typeRacerDAO().insertProfileImage(profileImage);
                listener.jobFinished(true);
            }
        }).start();
    }

    @Override
    public void insertDataProfileImages(final List<ProfileImage> profileImages) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                TypeRacerDatabase.getDatabase(context).typeRacerDAO().insertProfileImages(profileImages);
            }
        }).start();

    }

    @Override
    public void getDataProfileImages(final ProfileImageListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<ProfileImage> profileImages = TypeRacerDatabase.getDatabase(context).typeRacerDAO().getAllProfileImages();
                listener.getImages(profileImages);
            }
        }).start();

    }

    @Override
    public void getProfileImageByName(final String name, final ProfileImageListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<ProfileImage> profileImages = TypeRacerDatabase.getDatabase(context).typeRacerDAO().getProfileImageByName(name);
                listener.getImages(profileImages);
            }
        }).start();

    }

//    @Override
//    public void getData(String amount, String category, String difficulty, String type, CustomListenerRep listenerRep) {
//        List<Question> questions= QuestionDatabase.getQuestionDatabase(context).noteDao().getAllQuestionsByCategory(category);
//        Collections.shuffle(questions);
//        listenerRep.RepReturn(questions.subList(0,10));
//    }
//    @Override
//    public void insertData(List<Question> questionList) {
//        QuestionDatabase.getQuestionDatabase(context).noteDao().insertNotes(questionList);
//        Category category=new Category(questionList.get(0).getCategory(),questionList.size());
//        QuestionDatabase.getQuestionDatabase(context).noteDao().insertCategory(category);
//
//    }
//
//    @Override
//    public void getListOfCategories(CategoryListener listener) {
//        listener.getAllCategories(QuestionDatabase.getQuestionDatabase(context).noteDao().getAllCategories());
//    }




//
//    @Override
//    public void getData(ListenerRepositoryDataSource listenerRep) {
//     DataBaseHelper db= AppSingleton.getINSTANCE(context).getDbHelper();
//     List<Question>questions=db.getAllQuestions();
//        Log.e("DATABASE_QUESTION_COUNT",String.valueOf(questions.size()));
//     listenerRep.sendDataToRepository(questions);
//        db.close();
//    }
//
//    @Override
//    public void insertData(List<Question> questionList) {
//        DataBaseHelper db= AppSingleton.getINSTANCE(context).getDbHelper();
//            for(Question question:questionList){
//                db.insertQuestion(question);
//            }
//            db.close();
//    }
}
