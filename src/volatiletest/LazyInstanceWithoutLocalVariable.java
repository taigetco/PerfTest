package volatiletest;

public class LazyInstanceWithoutLocalVariable extends LazyInstance
{
  @Override
  protected Object getInitializedObject()
  {
    if (field == null)
    {
      synchronized (this)
      {
        if (field == null)
        {
          field = new Object();
        }
      }
    }
    return field;
  }
}
