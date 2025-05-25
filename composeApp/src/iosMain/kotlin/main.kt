import androidx.compose.ui.window.ComposeUIViewController
import com.oriooneee.paging.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
