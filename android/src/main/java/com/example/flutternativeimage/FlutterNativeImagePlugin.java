package com.example.flutternativeimage;

import android.content.Context;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

/**
 * FlutterNativeImagePlugin
 */
public class FlutterNativeImagePlugin implements FlutterPlugin {
  private static final String CHANNEL_NAME = "flutter_native_image";
  private MethodChannel channel;
  private Context context; // Store context if MethodCallHandlerImpl needs it after setup
  
  /**
   * Plugin registration.
   */
  // public static void registerWith(PluginRegistry.Registrar registrar) {
  //   final FlutterNativeImagePlugin plugin = new FlutterNativeImagePlugin();
  //   plugin.setupChannel(registrar.messenger(), registrar.context());
  // }

  @Override
  public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding binding) {
      // Get the application context
      this.context = binding.getApplicationContext();
      // Get the BinaryMessenger
      BinaryMessenger messenger = binding.getBinaryMessenger(); // Or binding.getFlutterEngine().getDartExecutor()
      // Setup the channel using the v2 binding
      setupChannel(messenger, this.context);
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding binding) {
    // Clean up resources
    teardownChannel();
    this.context = null; // Release context
}

  private void setupChannel(BinaryMessenger messenger, Context context) {
    channel = new MethodChannel(messenger, CHANNEL_NAME);
    // Assuming MethodCallHandlerImpl needs the context
    MethodCallHandlerImpl handler = new MethodCallHandlerImpl(context);
    channel.setMethodCallHandler(handler);
}

  private void teardownChannel() {
    if (channel != null) {
        channel.setMethodCallHandler(null);
        channel = null;
    }
}
}
