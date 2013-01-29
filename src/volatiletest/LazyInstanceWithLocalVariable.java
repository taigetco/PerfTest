package volatiletest;

public class LazyInstanceWithLocalVariable extends LazyInstance
{
  @Override
  protected Object getInitializedObject(){
    Object result = field;
    if (result == null)
    {
      synchronized (this)
      {
        result = field;
        if (result == null)
        {
          result = field = new Object();
        }
      }
    }
    return result;
  }
}
