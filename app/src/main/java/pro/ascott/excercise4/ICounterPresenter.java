package pro.ascott.excercise4;

public interface ICounterPresenter {
    void OnProgressUpdate(Integer counter);
    void OnPreProcessing(String text);
    void OnPostProcessing(String text);
}
