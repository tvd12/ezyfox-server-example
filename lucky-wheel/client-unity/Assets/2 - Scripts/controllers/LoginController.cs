using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class LoginController : MonoBehaviour
{
    public InputField username;
    public InputField password;
    public ClickEventButton loginButton;

    private void Awake()
    {
        loginButton.clickEvent += OnLoginButtonClick;
        PluginInfoHandler.socketSetupCompletedEvent += OnSocketSetupCompleted;
    }

    void OnLoginButtonClick()
    {
        // Login to socket server
        SocketProxy.getInstance().login(username.text, password.text);
    }

    void OnSocketSetupCompleted()
    {
        // Change scene here
        SceneManager.LoadScene("MainScene");
    }
}
