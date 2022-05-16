package com.iti.EShopper.Activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.iti.EShopper.R;
import com.iti.EShopper.helper.BaseActivity;
import com.iti.EShopper.helper.MyApplication;
import com.iti.EShopper.helper.WebEngine;
import com.iti.EShopper.helper.WebListener;

import java.util.Locale;

public class UseTerms extends BaseActivity {

    ProgressBar progressBar;

    private WebView mWebView;
    private WebEngine mWebEngine;

    String agreementsEn = "<p>your phone vulnerable to malware/viruses/malicious programs, compromise your phone&rsquo;s security features and it could mean that the Vira stayle app won&rsquo;t work properly or at all.</p>\n" +
            "\n" +
            "<p>The app does use third party services that declare their own Terms and Conditions.</p>\n" +
            "\n" +
            "<p>Link to Terms and Conditions of third party service providers used by the app</p>\n" +
            "\n" +
            "<ul>\n" +
            "\t<li><a href=\"https://policies.google.com/terms\" target=\"_blank\">Google Play Services</a></li>\n" +
            "\t<li><a href=\"https://developers.google.com/admob/terms\" target=\"_blank\">AdMob</a></li>\n" +
            "\t<li><a href=\"https://firebase.google.com/terms/analytics\" target=\"_blank\">Google Analytics for Firebase</a></li>\n" +
            "\t<li><a href=\"https://firebase.google.com/terms/crashlytics\" target=\"_blank\">Firebase Crashlytics</a></li>\n" +
            "</ul>\n" +
            "\n" +
            "<p>You should be aware that there are certain things that Zain phone store will not take responsibility for. Certain functions of the app will require the app to have an active internet connection. The connection can be Wi-Fi, or provided by your mobile network provider, but Zain phone store cannot take responsibility for the app not working at full functionality if you don&rsquo;t have access to Wi-Fi, and you don&rsquo;t have any of your data allowance left.</p>\n" +
            "\n" +
            "<p>If you&rsquo;re using the app outside of an area with Wi-Fi, you should remember that your terms of the agreement with your mobile network provider will still apply. As a result, you may be charged by your mobile provider for the cost of data for the duration of the connection while accessing the app, or other third party charges. In using the app, you&rsquo;re accepting responsibility for any such charges, including roaming data charges if you use the app outside of your home territory (i.e. region or country) without turning off data roaming. If you are not the bill payer for the device on which you&rsquo;re using the app, please be aware that we assume that you have received permission from the bill payer for using the app.</p>\n" +
            "\n" +
            "<p>Along the same lines, Zain phone store cannot always take responsibility for the way you use the app i.e. You need to make sure that your device stays charged &ndash; if it runs out of battery and you can&rsquo;t turn it on to avail the Service, Zain phone store cannot accept responsibility.</p>\n" +
            "\n" +
            "<p>With respect to Zain phone store&rsquo;s responsibility for your use of the app, when you&rsquo;re using the app, it&rsquo;s important to bear in mind that although we endeavour to ensure that it is updated and correct at all times, we do rely on third parties to provide information to us so that we can make it available to you. Zain phone store accepts no liability for any loss, direct or indirect, you experience as a result of relying wholly on this functionality of the app.</p>\n" +
            "\n" +
            "<p>At some point, we may wish to update the app. The app is currently available on Android &ndash; the requirements for system(and for any additional systems we decide to extend the availability of the app to) may change, and you&rsquo;ll need to download the updates if you want to keep using the app. Zain phone store does not promise that it will always update the app so that it is relevant to you and/or works with the Android version that you have installed on your device. However, you promise to always accept updates to the application when offered to you, We may also wish to stop providing the app, and may terminate use of it at any time without giving notice of termination to you. Unless we tell you otherwise, upon any termination, (a) the rights and licenses granted to you in these terms will end; (b) you must stop using the app, and (if needed) delete it from your device.</p>\n" +
            "\n" +
            "<p><strong>Changes to This Terms and Conditions</strong></p>\n" +
            "\n" +
            "<p>We may update our Terms and Conditions from time to time. Thus, you are advised to review this page periodically for any changes. We will notify you of any changes by posting the new Terms and Conditions on this page.</p>\n" +
            "\n" +
            "<p>These terms and conditions are effective as of 2021-03-22</p>\n" +
            "\n" +
            "<p><strong>Contact Us</strong></p>\n" +
            "\n" +
            "<p>If you have any questions or suggestions about our Terms and Conditions, do not hesitate to contact us at Zain phone store@web.de.</p>\n" +
            "\n" +
            "<p>This Terms and Conditions page was generated by&nbsp;<a href=\"https://app-privacy-policy-generator.nisrulz.com/\" target=\"_blank\">App Privacy Policy Generator</a></p>\n" +
            "\n" +
            "<p>&nbsp;</p>\n";



    String agreementsAr = "<p>هاتفك عرضة للبرامج الضارة / الفيروسات / البرامج الضارة ، مما يعرض ميزات أمان هاتفك للخطر وقد يعني ذلك أن تطبيق Vira stayle لن يعمل بشكل صحيح أو لن يعمل على الإطلاق.<br />\n" +
            "يستخدم التطبيق خدمات الجهات الخارجية التي تعلن عن الشروط والأحكام الخاصة بها.<br />\n" +
            "رابط لشروط وأحكام مزودي خدمة الطرف الثالث التي يستخدمها التطبيق<br />\n" +
            "&bull; خدمات Google Play<br />\n" +
            "&bull; AdMob<br />\n" +
            "&bull; Google Analytics for Firebase<br />\n" +
            "&bull; Firebase Crashlytics<br />\n" +
            "يجب أن تدرك أن هناك أشياء معينة لن يتحمل Zain phone store مسؤوليتها. تتطلب بعض وظائف التطبيق أن يكون للتطبيق اتصال إنترنت نشط. يمكن أن يكون الاتصال عبر Wi-Fi ، أو مقدمًا من مزود شبكة الهاتف المحمول الخاص بك ، ولكن لا يمكن لـ Zain phone store تحمل مسؤولية التطبيق الذي لا يعمل بكامل وظائفه إذا لم يكن لديك إمكانية الوصول إلى شبكة Wi-Fi ، ولم يكن لديك أي من ترك بدل البيانات.<br />\n" +
            "إذا كنت تستخدم التطبيق خارج منطقة مزودة بشبكة Wi-Fi ، فيجب أن تتذكر أن شروط الاتفاقية مع مزود شبكة الجوال الخاص بك ستظل سارية. نتيجة لذلك ، قد يتم تحميلك من قبل مزود خدمة الهاتف المحمول الخاص بك مقابل تكلفة البيانات طوال مدة الاتصال أثناء الوصول إلى التطبيق ، أو رسوم الطرف الثالث الأخرى. عند استخدام التطبيق ، فأنت تقبل المسؤولية عن أي رسوم من هذا القبيل ، بما في ذلك رسوم بيانات التجوال إذا كنت تستخدم التطبيق خارج إقليمك الأصلي (أي المنطقة أو البلد) دون إيقاف تجوال البيانات. إذا لم تكن دافع الفاتورة للجهاز الذي تستخدم التطبيق عليه ، فيرجى العلم أننا نفترض أنك قد تلقيت إذنًا من دافع الفاتورة لاستخدام التطبيق.<br />\n" +
            "على نفس المنوال ، لا يمكن لـ Zain phone store دائمًا تحمل مسؤولية الطريقة التي تستخدم بها التطبيق ، أي أنك تحتاج إلى التأكد من بقاء جهازك مشحونًا - إذا نفدت البطارية ولا يمكنك تشغيله للاستفادة من الخدمة ، فلا يمكن لـ Zain phone store قبول المسؤولية.<br />\n" +
            "فيما يتعلق بمسؤولية Zain phone store عن استخدامك للتطبيق ، عند استخدام التطبيق ، من المهم أن تضع في اعتبارك أنه على الرغم من أننا نسعى لضمان تحديثه وصحته في جميع الأوقات ، فإننا نعتمد على جهات خارجية لتقديم معلومات لنا حتى نتمكن من إتاحتها لك. لا تتحمل Zain phone store أي مسؤولية عن أي خسارة ، مباشرة أو غير مباشرة ، تتعرض لها نتيجة الاعتماد كليًا على وظيفة التطبيق هذه.<br />\n" +
            "في مرحلة ما ، قد نرغب في تحديث التطبيق. التطبيق متاح حاليًا على نظام Android - قد تتغير متطلبات النظام (وأي أنظمة إضافية نقرر تمديد توفر التطبيق عليها) ، وستحتاج إلى تنزيل التحديثات إذا كنت تريد الاستمرار في استخدام التطبيق. لا تعد Zain phone store بأنها ستقوم دائمًا بتحديث التطبيق بحيث يكون مناسبًا لك و / أو يعمل مع إصدار Android الذي قمت بتثبيته على جهازك. ومع ذلك ، فإنك تتعهد بقبول تحديثات التطبيق دائمًا عند عرضها عليك ، وقد نرغب أيضًا في التوقف عن توفير التطبيق ، وقد ننهي استخدامه في أي وقت دون إرسال إشعار بالإنهاء إليك. ما لم نخبرك بخلاف ذلك ، عند أي إنهاء ، (أ) تنتهي الحقوق والتراخيص الممنوحة لك في هذه الشروط ؛ (ب) يجب عليك التوقف عن استخدام التطبيق ، و (إذا لزم الأمر) حذفه من جهازك.<br />\n" +
            "التغييرات على هذه الشروط والأحكام<br />\n" +
            "قد نقوم بتحديث الشروط والأحكام الخاصة بنا من وقت لآخر. وبالتالي ، يُنصح بمراجعة هذه الصفحة بشكل دوري لمعرفة أي تغييرات. سنخطرك بأي تغييرات عن طريق نشر الشروط والأحكام الجديدة على هذه الصفحة.<br />\n" +
            "تسري هذه الشروط والأحكام اعتبارًا من 2021-03-22<br />\n" +
            "اتصل بنا<br />\n" +
            "إذا كانت لديك أي أسئلة أو اقتراحات حول الشروط والأحكام الخاصة بنا ، فلا تتردد في الاتصال بنا على Zain phone store@web.de.</p>\n";




    String agreementsGr = "<p>Ihr Telefon ist anf&auml;llig f&uuml;r Malware / Viren / Schadprogramme, beeintr&auml;chtigt die Sicherheitsfunktionen Ihres Telefons und kann dazu f&uuml;hren, dass die Vira Stayle-App nicht richtig oder &uuml;berhaupt nicht funktioniert.<br />\n" +
            "Die App verwendet Dienste von Drittanbietern, die ihre eigenen Allgemeinen Gesch&auml;ftsbedingungen deklarieren.<br />\n" +
            "Link zu den Allgemeinen Gesch&auml;ftsbedingungen von Drittanbietern, die von der App verwendet werden<br />\n" +
            "&bull; Google Play-Dienste<br />\n" +
            "&bull; AdMob<br />\n" +
            "&bull; Google Analytics f&uuml;r Firebase<br />\n" +
            "&bull; Firebase Crashlytics<br />\n" +
            "Sie sollten sich bewusst sein, dass es bestimmte Dinge gibt, f&uuml;r die Zain phone store keine Verantwortung &uuml;bernimmt. F&uuml;r bestimmte Funktionen der App muss die App &uuml;ber eine aktive Internetverbindung verf&uuml;gen. Die Verbindung kann Wi-Fi sein oder von Ihrem Mobilfunkanbieter bereitgestellt werden. Zain phone store kann jedoch keine Verantwortung daf&uuml;r &uuml;bernehmen, dass die App nicht mit voller Funktionalit&auml;t funktioniert, wenn Sie keinen Zugang zu Wi-Fi haben und keine von Ihnen haben Datenmenge &uuml;brig.<br />\n" +
            "Wenn Sie die App au&szlig;erhalb eines Wi-Fi-Bereichs verwenden, sollten Sie beachten, dass Ihre Vertragsbedingungen mit Ihrem Mobilfunkanbieter weiterhin gelten. Infolgedessen werden Ihnen m&ouml;glicherweise von Ihrem Mobilfunkanbieter die Datenkosten f&uuml;r die Dauer der Verbindung beim Zugriff auf die App oder andere Geb&uuml;hren Dritter in Rechnung gestellt. Mit der Nutzung der App &uuml;bernehmen Sie die Verantwortung f&uuml;r solche Geb&uuml;hren, einschlie&szlig;lich Roaming-Datengeb&uuml;hren, wenn Sie die App au&szlig;erhalb Ihres Heimatgebiets (d. H. Region oder Land) verwenden, ohne das Datenroaming zu deaktivieren. Wenn Sie nicht der Rechnungszahler f&uuml;r das Ger&auml;t sind, auf dem Sie die App verwenden, beachten Sie bitte, dass wir davon ausgehen, dass Sie vom Rechnungszahler die Erlaubnis zur Nutzung der App erhalten haben.<br />\n" +
            "In diesem Sinne kann Zain phone store nicht immer die Verantwortung f&uuml;r die Art und Weise &uuml;bernehmen, wie Sie die App verwenden. Sie m&uuml;ssen also sicherstellen, dass Ihr Ger&auml;t aufgeladen bleibt. Wenn der Akku leer ist und Sie es nicht einschalten k&ouml;nnen, um den Service in Anspruch zu nehmen, kann Zain phone store dies nicht Verantwortung &uuml;bernehmen.<br />\n" +
            "In Bezug auf die Verantwortung von Zain phone store f&uuml;r Ihre Nutzung der App ist es wichtig zu ber&uuml;cksichtigen, dass wir uns bei der Nutzung der App darauf verlassen, dass sie jederzeit aktualisiert und korrekt ist. Wir sind jedoch auf die Bereitstellung durch Dritte angewiesen Informationen an uns, damit wir sie Ihnen zur Verf&uuml;gung stellen k&ouml;nnen. Zain phone store &uuml;bernimmt keine Haftung f&uuml;r direkte oder indirekte Verluste, die dadurch entstehen, dass Sie sich vollst&auml;ndig auf diese Funktionalit&auml;t der App verlassen.<br />\n" +
            "Irgendwann m&ouml;chten wir m&ouml;glicherweise die App aktualisieren. Die App ist derzeit auf Android verf&uuml;gbar. Die Anforderungen an das System (und f&uuml;r alle zus&auml;tzlichen Systeme, auf die wir die Verf&uuml;gbarkeit der App erweitern m&ouml;chten) k&ouml;nnen sich &auml;ndern. Sie m&uuml;ssen die Updates herunterladen, wenn Sie die App weiterhin verwenden m&ouml;chten. Zain phone store verspricht nicht, die App immer so zu aktualisieren, dass sie f&uuml;r Sie relevant ist und / oder mit der auf Ihrem Ger&auml;t installierten Android-Version funktioniert. Sie versprechen jedoch, Aktualisierungen der Anwendung immer zu akzeptieren, wenn sie Ihnen angeboten werden. Wir m&ouml;chten m&ouml;glicherweise auch die Bereitstellung der App einstellen und die Nutzung jederzeit beenden, ohne Sie dar&uuml;ber zu informieren. Sofern wir Ihnen nichts anderes mitteilen, enden bei einer K&uuml;ndigung (a) die Ihnen in diesen Bedingungen gew&auml;hrten Rechte und Lizenzen; (b) Sie m&uuml;ssen die App nicht mehr verwenden und (falls erforderlich) von Ihrem Ger&auml;t l&ouml;schen.<br />\n" +
            "&Auml;nderungen dieser Allgemeinen Gesch&auml;ftsbedingungen<br />\n" +
            "Wir k&ouml;nnen unsere Allgemeinen Gesch&auml;ftsbedingungen von Zeit zu Zeit aktualisieren. Es wird daher empfohlen, diese Seite regelm&auml;&szlig;ig auf &Auml;nderungen zu &uuml;berpr&uuml;fen. Wir werden Sie &uuml;ber &Auml;nderungen informieren, indem wir die neuen Allgemeinen Gesch&auml;ftsbedingungen auf dieser Seite ver&ouml;ffentlichen.<br />\n" +
            "Diese Allgemeinen Gesch&auml;ftsbedingungen gelten ab dem 2021-03-22<br />\n" +
            "Kontaktiere uns<br />\n" +
            "Wenn Sie Fragen oder Anregungen zu unseren Allgemeinen Gesch&auml;ftsbedingungen haben, z&ouml;gern Sie nicht, uns unter Zain phone store@web.de zu kontaktieren.</p>\n";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_terms);

        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

        initWebEngine();


        if (Locale.getDefault().getLanguage().equals("en")){
            MyApplication.setPreferencesBoolean("Arabic",false);
            mWebEngine.loadHtml(agreementsEn);
        }else if (Locale.getDefault().getLanguage().equals("ar")){
            MyApplication.setPreferencesBoolean("Arabic",true);
            mWebEngine.loadHtml(agreementsAr);
        }else if (Locale.getDefault().getLanguage().equals("ge")){
            MyApplication.setPreferencesBoolean("Arabic",false);
            mWebEngine.loadHtml(agreementsGr);
        }else {
            MyApplication.setPreferencesBoolean("Arabic",false);
            mWebEngine.loadHtml(agreementsEn);
        }
    }

    public void initWebEngine() {

        mWebView = (WebView) findViewById(R.id.web_view);

        mWebEngine = new WebEngine(mWebView, UseTerms.this);
        mWebEngine.initWebView();


        mWebEngine.initListeners(new WebListener() {
            @Override
            public void onStart() {
                showLoading("Loading");
            }

            @Override
            public void onLoaded() {
                hideLoading();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onProgress(int progress) {
            }

            @Override
            public void onNetworkError() {
                //showEmptyView();
            }

            @Override
            public void onPageTitle(String title) {
            }
        });
    }


}