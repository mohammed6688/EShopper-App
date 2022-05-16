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

public class AgreementsActivity extends BaseActivity {


    ProgressBar progressBar;

    private WebView mWebView;
    private WebEngine mWebEngine;

    String agreementsAr = "<p>سياسة خاصة<br />\n" +
            "صممت Zain phone store تطبيق Zain phone store كتطبيق تجاري. هذه الخدمة مقدمة من Zain phone store وهي مخصصة للاستخدام كما هي.</p>\n" +
            "\n" +
            "<p>تُستخدم هذه الصفحة لإعلام الزائرين بسياساتنا بجمع المعلومات الشخصية واستخدامها والكشف عنها إذا قرر أي شخص استخدام خدمتنا.</p>\n" +
            "\n" +
            "<p>إذا اخترت استخدام خدمتنا ، فأنت توافق على جمع واستخدام المعلومات المتعلقة بهذه السياسة. تُستخدم المعلومات الشخصية التي نجمعها لتوفير الخدمة وتحسينها. لن نستخدم أو نشارك معلوماتك مع أي شخص باستثناء ما هو موضح في سياسة الخصوصية هذه.</p>\n" +
            "\n" +
            "<p>المصطلحات المستخدمة في سياسة الخصوصية هذه لها نفس المعاني الواردة في الشروط والأحكام الخاصة بنا ، والتي يمكن الوصول إليها في Vira stayle ما لم يتم تحديد خلاف ذلك في سياسة الخصوصية هذه.</p>\n" +
            "\n" +
            "<p>جمع المعلومات واستخدامها</p>\n" +
            "\n" +
            "<p>للحصول على تجربة أفضل ، أثناء استخدام خدمتنا ، قد نطلب منك تزويدنا بمعلومات تعريف شخصية معينة ، بما في ذلك على سبيل المثال لا الحصر ، رقم الهاتف والاسم والعنوان ومعلومات الجهاز ومعلومات السجل ومعلومات الموقع وأرقام التطبيق الفريدة. سيتم الاحتفاظ بالمعلومات التي نطلبها من قبلنا واستخدامها كما هو موضح في سياسة الخصوصية هذه.</p>\n" +
            "\n" +
            "<p>يستخدم التطبيق خدمات الجهات الخارجية التي قد تجمع المعلومات المستخدمة لتحديد هويتك.</p>\n" +
            "\n" +
            "<p>رابط لسياسة الخصوصية لمقدمي خدمات الطرف الثالث التي يستخدمها التطبيق</p>\n" +
            "\n" +
            "<p>خدمات جوجل بلاي<br />\n" +
            "AdMob<br />\n" +
            "Google Analytics for Firebase<br />\n" +
            "Firebase Crashlytics<br />\n" +
            "تسجيل البيانات</p>\n" +
            "\n" +
            "<p>نود إعلامك أنه كلما استخدمت خدمتنا ، في حالة حدوث خطأ في التطبيق ، نقوم بجمع البيانات والمعلومات (من خلال منتجات الجهات الخارجية) على هاتفك تسمى Log Data. قد تتضمن بيانات السجل هذه معلومات مثل عنوان بروتوكول الإنترنت (&quot;IP&quot;) الخاص بجهازك ، واسم الجهاز ، وإصدار نظام التشغيل ، وتكوين التطبيق عند استخدام خدمتنا ، ووقت وتاريخ استخدامك للخدمة ، وإحصاءات أخرى .</p>\n" +
            "\n" +
            "<p>بسكويت</p>\n" +
            "\n" +
            "<p>ملفات تعريف الارتباط هي ملفات تحتوي على كمية صغيرة من البيانات التي يتم استخدامها بشكل شائع كمعرفات فريدة مجهولة الهوية. يتم إرسالها إلى متصفحك من مواقع الويب التي تزورها ويتم تخزينها على الذاكرة الداخلية لجهازك.</p>\n" +
            "\n" +
            "<p>لا تستخدم هذه الخدمة &quot;ملفات تعريف الارتباط&quot; بشكل صريح. ومع ذلك ، قد يستخدم التطبيق رموزًا ومكتبات خاصة بطرف ثالث تستخدم &quot;ملفات تعريف الارتباط&quot; لجمع المعلومات وتحسين خدماتهم. لديك خيار إما قبول أو رفض ملفات تعريف الارتباط هذه ومعرفة متى يتم إرسال ملف تعريف الارتباط إلى جهازك. إذا اخترت رفض ملفات تعريف الارتباط الخاصة بنا ، فقد لا تتمكن من استخدام بعض أجزاء هذه الخدمة.</p>\n" +
            "\n" +
            "<p>مقدمي الخدمة</p>\n" +
            "\n" +
            "<p>يجوز لنا توظيف شركات وأفراد تابعين لجهات خارجية للأسباب التالية:</p>\n" +
            "\n" +
            "<p>لتسهيل خدمتنا ؛<br />\n" +
            "لتقديم الخدمة نيابة عنا ؛<br />\n" +
            "لأداء الخدمات المتعلقة بالخدمة ؛ أو<br />\n" +
            "لمساعدتنا في تحليل كيفية استخدام خدمتنا.<br />\n" +
            "نريد إبلاغ مستخدمي هذه الخدمة أن هذه الأطراف الثالثة لديها حق الوصول إلى معلوماتك الشخصية. والسبب هو أداء المهام الموكلة إليهم نيابة عنا. ومع ذلك ، فهم ملزمون بعدم الكشف عن المعلومات أو استخدامها لأي غرض آخر.</p>\n" +
            "\n" +
            "<p>حماية</p>\n" +
            "\n" +
            "<p>نحن نقدر ثقتك في تزويدنا بمعلوماتك الشخصية ، وبالتالي فإننا نسعى جاهدين لاستخدام وسائل مقبولة تجاريًا لحمايتها. لكن تذكر أنه لا توجد وسيلة نقل عبر الإنترنت أو طريقة تخزين إلكتروني آمنة وموثوقة بنسبة 100٪ ، ولا يمكننا ضمان أمانها المطلق.</p>\n" +
            "\n" +
            "<p>روابط لمواقع أخرى</p>\n" +
            "\n" +
            "<p>قد تحتوي هذه الخدمة على روابط لمواقع أخرى. إذا قمت بالنقر فوق ارتباط جهة خارجية ، فسيتم توجيهك إلى هذا الموقع. لاحظ أن هذه المواقع الخارجية لا يتم تشغيلها بواسطتنا. لذلك ، ننصحك بشدة بمراجعة سياسة الخصوصية الخاصة بهذه المواقع. ليس لدينا أي سيطرة ولا نتحمل أي مسؤولية عن المحتوى أو سياسات الخصوصية أو الممارسات الخاصة بأي مواقع أو خدمات تابعة لجهات خارجية.</p>\n" +
            "\n" +
            "<p>خصوصية الأطفال</p>\n" +
            "\n" +
            "<p>لا تخاطب هذه الخدمات أي شخص يقل عمره عن 13 عامًا. نحن لا نجمع عن عمد معلومات تعريف شخصية من الأطفال دون سن 13 عامًا. في حالة اكتشافنا أن طفلًا أقل من 13 عامًا قد زودنا بمعلومات شخصية ، فإننا نحذفها على الفور من خوادمنا. إذا كنت والدًا أو وصيًا وكنت تعلم أن طفلك قد زودنا بمعلومات شخصية ، فيرجى الاتصال بنا حتى نتمكن من القيام بالإجراءات اللازمة.</p>\n" +
            "\n" +
            "<p>التغييرات على سياسة الخصوصية هذه</p>\n" +
            "\n" +
            "<p>قد نقوم بتحديث سياسة الخصوصية الخاصة بنا من وقت لآخر. وبالتالي ، يُنصح بمراجعة هذه الصفحة بشكل دوري لمعرفة أي تغييرات. سنخطرك بأي تغييرات عن طريق نشر سياسة الخصوصية الجديدة على هذه الصفحة.</p>\n" +
            "\n" +
            "<p>هذه السياسة سارية اعتبارًا من 2021-03-22</p>\n" +
            "\n" +
            "<p>اتصل بنا</p>\n" +
            "\n" +
            "<p>إذا كان لديك أي أسئلة أو اقتراحات حول سياسة الخصوصية الخاصة بنا ، فلا تتردد في الاتصال بنا على virastayle@web.de.</p>\n";



    String agreementsEn = "<!DOCTYPE html>\n" +
            "    <html>\n" +
            "    <head>\n" +
            "      <meta charset='utf-8'>\n" +
            "      <meta name='viewport' content='width=device-width'>\n" +
            "      <title>Privacy Policy</title>\n" +
            "      <style> body { font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; padding:1em; } </style>\n" +
            "    </head>\n" +
            "    <body>\n" +
            "    <strong>Privacy Policy</strong> <p>\n" +
            "                  Zain phone store built the Zain phone store app as\n" +
            "                  a Commercial app. This SERVICE is provided by\n" +
            "                  Zain phone store  and is intended for use as\n" +
            "                  is.\n" +
            "                </p> <p>\n" +
            "                  This page is used to inform visitors regarding our\n" +
            "                  policies with the collection, use, and disclosure of Personal\n" +
            "                  Information if anyone decided to use our Service.\n" +
            "                </p> <p>\n" +
            "                  If you choose to use our Service, then you agree to\n" +
            "                  the collection and use of information in relation to this\n" +
            "                  policy. The Personal Information that we collect is\n" +
            "                  used for providing and improving the Service. We will not use or share your information with\n" +
            "                  anyone except as described in this Privacy Policy.\n" +
            "                </p> <p>\n" +
            "                  The terms used in this Privacy Policy have the same meanings\n" +
            "                  as in our Terms and Conditions, which is accessible at\n" +
            "                  Vira stayle unless otherwise defined in this Privacy Policy.\n" +
            "                </p> <p><strong>Information Collection and Use</strong></p> <p>\n" +
            "                  For a better experience, while using our Service, we\n" +
            "                  may require you to provide us with certain personally\n" +
            "                  identifiable information, including but not limited to Phone Number, name, address, Device information, Log information, Location information, Unique application numbers. The information that\n" +
            "                  we request will be retained by us and used as described in this privacy policy.\n" +
            "                </p> <div><p>\n" +
            "                    The app does use third party services that may collect\n" +
            "                    information used to identify you.\n" +
            "                  </p> <p>\n" +
            "                    Link to privacy policy of third party service providers used\n" +
            "                    by the app\n" +
            "                  </p> <ul><li><a href=\"https://www.google.com/policies/privacy/\" target=\"_blank\" rel=\"noopener noreferrer\">Google Play Services</a></li><li><a href=\"https://support.google.com/admob/answer/6128543?hl=en\" target=\"_blank\" rel=\"noopener noreferrer\">AdMob</a></li><li><a href=\"https://firebase.google.com/policies/analytics\" target=\"_blank\" rel=\"noopener noreferrer\">Google Analytics for Firebase</a></li><li><a href=\"https://firebase.google.com/support/privacy/\" target=\"_blank\" rel=\"noopener noreferrer\">Firebase Crashlytics</a></li><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----></ul></div> <p><strong>Log Data</strong></p> <p>\n" +
            "                  We want to inform you that whenever you\n" +
            "                  use our Service, in a case of an error in the app\n" +
            "                  we collect data and information (through third party\n" +
            "                  products) on your phone called Log Data. This Log Data may\n" +
            "                  include information such as your device Internet Protocol\n" +
            "                  (“IP”) address, device name, operating system version, the\n" +
            "                  configuration of the app when utilizing our Service,\n" +
            "                  the time and date of your use of the Service, and other\n" +
            "                  statistics.\n" +
            "                </p> <p><strong>Cookies</strong></p> <p>\n" +
            "                  Cookies are files with a small amount of data that are\n" +
            "                  commonly used as anonymous unique identifiers. These are sent\n" +
            "                  to your browser from the websites that you visit and are\n" +
            "                  stored on your device's internal memory.\n" +
            "                </p> <p>\n" +
            "                  This Service does not use these “cookies” explicitly. However,\n" +
            "                  the app may use third party code and libraries that use\n" +
            "                  “cookies” to collect information and improve their services.\n" +
            "                  You have the option to either accept or refuse these cookies\n" +
            "                  and know when a cookie is being sent to your device. If you\n" +
            "                  choose to refuse our cookies, you may not be able to use some\n" +
            "                  portions of this Service.\n" +
            "                </p> <p><strong>Service Providers</strong></p> <p>\n" +
            "                  We may employ third-party companies and\n" +
            "                  individuals due to the following reasons:\n" +
            "                </p> <ul><li>To facilitate our Service;</li> <li>To provide the Service on our behalf;</li> <li>To perform Service-related services; or</li> <li>To assist us in analyzing how our Service is used.</li></ul> <p>\n" +
            "                  We want to inform users of this Service\n" +
            "                  that these third parties have access to your Personal\n" +
            "                  Information. The reason is to perform the tasks assigned to\n" +
            "                  them on our behalf. However, they are obligated not to\n" +
            "                  disclose or use the information for any other purpose.\n" +
            "                </p> <p><strong>Security</strong></p> <p>\n" +
            "                  We value your trust in providing us your\n" +
            "                  Personal Information, thus we are striving to use commercially\n" +
            "                  acceptable means of protecting it. But remember that no method\n" +
            "                  of transmission over the internet, or method of electronic\n" +
            "                  storage is 100% secure and reliable, and we cannot\n" +
            "                  guarantee its absolute security.\n" +
            "                </p> <p><strong>Links to Other Sites</strong></p> <p>\n" +
            "                  This Service may contain links to other sites. If you click on\n" +
            "                  a third-party link, you will be directed to that site. Note\n" +
            "                  that these external sites are not operated by us.\n" +
            "                  Therefore, we strongly advise you to review the\n" +
            "                  Privacy Policy of these websites. We have\n" +
            "                  no control over and assume no responsibility for the content,\n" +
            "                  privacy policies, or practices of any third-party sites or\n" +
            "                  services.\n" +
            "                </p> <p><strong>Children’s Privacy</strong></p> <p>\n" +
            "                  These Services do not address anyone under the age of 13.\n" +
            "                  We do not knowingly collect personally\n" +
            "                  identifiable information from children under 13 years of age. In the case\n" +
            "                  we discover that a child under 13 has provided\n" +
            "                  us with personal information, we immediately\n" +
            "                  delete this from our servers. If you are a parent or guardian\n" +
            "                  and you are aware that your child has provided us with\n" +
            "                  personal information, please contact us so that\n" +
            "                  we will be able to do necessary actions.\n" +
            "                </p> <p><strong>Changes to This Privacy Policy</strong></p> <p>\n" +
            "                  We may update our Privacy Policy from\n" +
            "                  time to time. Thus, you are advised to review this page\n" +
            "                  periodically for any changes. We will\n" +
            "                  notify you of any changes by posting the new Privacy Policy on\n" +
            "                  this page.\n" +
            "                </p> <p>This policy is effective as of 2021-03-22</p> <p><strong>Contact Us</strong></p> <p>\n" +
            "                  If you have any questions or suggestions about our\n" +
            "                  Privacy Policy, do not hesitate to contact us at virastayle@web.de.\n" +
            "                </p> <p>This privacy policy page was created at <a href=\"https://privacypolicytemplate.net\" target=\"_blank\" rel=\"noopener noreferrer\">privacypolicytemplate.net </a>and modified/generated by <a href=\"https://app-privacy-policy-generator.nisrulz.com/\" target=\"_blank\" rel=\"noopener noreferrer\">App Privacy Policy Generator</a></p>\n" +
            "    </body>\n" +
            "    </html>\n" +
            "      ";

    String agreementsGr = "Datenschutz-Bestimmungen\n" +
            "virastayle hat die Vira Stayle App als kommerzielle App erstellt. Dieser SERVICE wird von virastayle bereitgestellt und ist für die unveränderte Verwendung vorgesehen.\n" +
            "\n" +
            "Diese Seite wird verwendet, um Besucher über unsere Richtlinien bezüglich der Erfassung, Verwendung und Offenlegung persönlicher Informationen zu informieren, falls sich jemand für die Nutzung unseres Dienstes entschieden hat.\n" +
            "\n" +
            "Wenn Sie sich für die Nutzung unseres Dienstes entscheiden, stimmen Sie der Erfassung und Verwendung von Informationen in Bezug auf diese Richtlinie zu. Die von uns gesammelten persönlichen Daten werden zur Bereitstellung und Verbesserung des Dienstes verwendet. Wir werden Ihre Daten nicht verwenden oder an Dritte weitergeben, außer wie in dieser Datenschutzrichtlinie beschrieben.\n" +
            "\n" +
            "Die in dieser Datenschutzrichtlinie verwendeten Begriffe haben die gleiche Bedeutung wie in unseren Allgemeinen Geschäftsbedingungen, auf die bei Vira Stayle zugegriffen werden kann, sofern in dieser Datenschutzrichtlinie nichts anderes definiert ist.\n" +
            "\n" +
            "Sammlung und Verwendung von Informationen\n" +
            "\n" +
            "Für eine bessere Erfahrung bei der Nutzung unseres Dienstes müssen Sie uns möglicherweise bestimmte personenbezogene Daten zur Verfügung stellen, einschließlich, aber nicht beschränkt auf Telefonnummer, Name, Adresse, Geräteinformationen, Protokollinformationen, Standortinformationen und eindeutige Anwendungsnummern. Die von uns angeforderten Informationen werden von uns gespeichert und wie in dieser Datenschutzrichtlinie beschrieben verwendet.\n" +
            "\n" +
            "Die App verwendet Dienste von Drittanbietern, die möglicherweise Informationen sammeln, mit denen Sie identifiziert werden.\n" +
            "\n" +
            "Link zu Datenschutzbestimmungen von Drittanbietern, die von der App verwendet werden\n" +
            "\n" +
            "Google Play Services\n" +
            "AdMob\n" +
            "Google Analytics für Firebase\n" +
            "Firebase Crashlytics\n" +
            "Logdaten\n" +
            "\n" +
            "Wir möchten Sie darüber informieren, dass wir bei jeder Nutzung unseres Dienstes im Falle eines Fehlers in der App Daten und Informationen (über Produkte von Drittanbietern) auf Ihrem Telefon namens Log Data erfassen. Diese Protokolldaten können Informationen wie die IP-Adresse (Device Internet Protocol) Ihres Geräts, den Gerätenamen, die Betriebssystemversion, die Konfiguration der App bei Nutzung unseres Dienstes, die Uhrzeit und das Datum Ihrer Nutzung des Dienstes sowie andere Statistiken enthalten .\n" +
            "\n" +
            "Kekse\n" +
            "\n" +
            "Cookies sind Dateien mit einer kleinen Datenmenge, die üblicherweise als anonyme eindeutige Kennungen verwendet werden. Diese werden von den von Ihnen besuchten Websites an Ihren Browser gesendet und im internen Speicher Ihres Geräts gespeichert.\n" +
            "\n" +
            "Dieser Service verwendet diese „Cookies“ nicht explizit. Die App verwendet jedoch möglicherweise Code und Bibliotheken von Drittanbietern, die „Cookies“ verwenden, um Informationen zu sammeln und ihre Dienste zu verbessern. Sie haben die Möglichkeit, diese Cookies entweder zu akzeptieren oder abzulehnen und zu erfahren, wann ein Cookie an Ihr Gerät gesendet wird. Wenn Sie unsere Cookies ablehnen, können Sie möglicherweise einige Teile dieses Dienstes nicht nutzen.\n" +
            "\n" +
            "Dienstleister\n" +
            "\n" +
            "Wir können aus folgenden Gründen Drittunternehmen und Einzelpersonen beschäftigen:\n" +
            "\n" +
            "Um unseren Service zu erleichtern;\n" +
            "Den Service in unserem Namen bereitzustellen;\n" +
            "Servicebezogene Dienstleistungen erbringen; oder\n" +
            "Um uns bei der Analyse der Nutzung unseres Service zu unterstützen.\n" +
            "Wir möchten Benutzer dieses Dienstes darüber informieren, dass diese Dritten Zugriff auf Ihre persönlichen Daten haben. Der Grund ist, die ihnen zugewiesenen Aufgaben in unserem Namen auszuführen. Sie sind jedoch verpflichtet, die Informationen nicht weiterzugeben oder für andere Zwecke zu verwenden.\n" +
            "\n" +
            "Sicherheit\n" +
            "\n" +
            "Wir schätzen Ihr Vertrauen in die Bereitstellung Ihrer persönlichen Daten und bemühen uns daher, wirtschaftlich akzeptable Mittel zum Schutz dieser Daten zu verwenden. Denken Sie jedoch daran, dass keine Methode zur Übertragung über das Internet oder zur elektronischen Speicherung 100% sicher und zuverlässig ist und wir keine absolute Sicherheit garantieren können.\n" +
            "\n" +
            "Links zu anderen Seiten\n" +
            "\n" +
            "Dieser Service kann Links zu anderen Websites enthalten. Wenn Sie auf einen Link eines Drittanbieters klicken, werden Sie zu dieser Site weitergeleitet. Beachten Sie, dass diese externen Websites nicht von uns betrieben werden. Wir empfehlen Ihnen daher dringend, die Datenschutzbestimmungen dieser Websites zu lesen. Wir haben keine Kontrolle über Inhalte, Datenschutzrichtlinien oder Praktiken von Websites oder Diensten Dritter und übernehmen keine Verantwortung dafür.\n" +
            "\n" +
            "Datenschutz für Kinder\n" +
            "\n" +
            "Diese Dienste richten sich nicht an Personen unter 13 Jahren. Wir sammeln wissentlich keine personenbezogenen Daten von Kindern unter 13 Jahren. Falls wir feststellen, dass ein Kind unter 13 Jahren uns personenbezogene Daten zur Verfügung gestellt hat, löschen wir diese sofort von unseren Servern. Wenn Sie Eltern oder Erziehungsberechtigte sind und wissen, dass Ihr Kind uns personenbezogene Daten zur Verfügung gestellt hat, setzen Sie sich bitte mit uns in Verbindung, damit wir die erforderlichen Maßnahmen ergreifen können.\n" +
            "\n" +
            "Änderungen dieser Datenschutzerklärung\n" +
            "\n" +
            "Wir können unsere Datenschutzrichtlinie von Zeit zu Zeit aktualisieren. Es wird daher empfohlen, diese Seite regelmäßig auf Änderungen zu überprüfen. Wir werden Sie über Änderungen informieren, indem wir die neue Datenschutzrichtlinie auf dieser Seite veröffentlichen.\n" +
            "\n" +
            "Diese Richtlinie gilt ab dem 22.03.2021\n" +
            "\n" +
            "Kontaktiere uns\n" +
            "\n" +
            "Wenn Sie Fragen oder Anregungen zu unserer Datenschutzrichtlinie haben, zögern Sie nicht, uns unter virastayle@web.de zu kontaktieren.";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreements);

        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

        initWebEngine();


        if (Locale.getDefault().getLanguage().equals("en")){
            MyApplication.setPreferencesBoolean("Arabic",false);
            mWebEngine.loadHtml(agreementsEn);
        }else if (Locale.getDefault().getLanguage().equals("ar")){
            MyApplication.setPreferencesBoolean("Arabic",true);
            mWebEngine.loadHtml(agreementsAr);
        }else {
            MyApplication.setPreferencesBoolean("Arabic",false);
            mWebEngine.loadHtml(agreementsEn);
        }
    }

    public void initWebEngine() {

        mWebView = (WebView) findViewById(R.id.web_view);

        mWebEngine = new WebEngine(mWebView, AgreementsActivity.this);
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