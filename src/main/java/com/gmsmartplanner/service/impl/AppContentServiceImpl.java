package com.gmsmartplanner.service.impl;

import com.gmsmartplanner.dto.response.AppContentResponseDTO;
import com.gmsmartplanner.service.AppContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppContentServiceImpl
        implements AppContentService {

    @Override
    public AppContentResponseDTO
    getAboutUs(

            String languageCode

    ) {

        return AppContentResponseDTO
                .builder()

                .content(

                        switch (

                                languageCode
                                        .toLowerCase()

                                ) {

                            case "hi" -> ABOUT_US_HI;

                            case "gu" -> ABOUT_US_GU;

                            default -> ABOUT_US_EN;
                        }
                )

                .build();
    }

    @Override
    public AppContentResponseDTO
    getPrivacyPolicy(

            String languageCode

    ) {

        return AppContentResponseDTO
                .builder()

                .content(

                        switch (

                                languageCode
                                        .toLowerCase()

                                ) {

                            case "hi" -> PRIVACY_HI;

                            case "gu" -> PRIVACY_GU;

                            default -> PRIVACY_EN;
                        }
                )

                .build();
    }

    @Override
    public AppContentResponseDTO
    getTermsAndConditions(

            String languageCode

    ) {

        return AppContentResponseDTO
                .builder()

                .content(

                        switch (

                                languageCode
                                        .toLowerCase()

                                ) {

                            case "hi" -> TERMS_HI;

                            case "gu" -> TERMS_GU;

                            default -> TERMS_EN;
                        }
                )

                .build();
    }

    private static final String ABOUT_US_EN =
            """
                    <body>
                    
                                                          <p>
                                                          Welcome to <b>GM Smart Planner</b>, an innovative productivity and financial management mobile application designed to simplify your daily life.\s
                                                          We are dedicated to helping individuals stay organized, focused, and financially aware.
                                                          </p>
                    
                                                          <p>
                                                          <b>GM Technosys</b> is passionate about creating smart and efficient digital solutions.\s
                                                          Our goal is to deliver tools that seamlessly combine productivity and financial discipline in one intuitive platform.
                                                          </p>
                    
                                                          <p>
                                                          Built with Flutter, GM Smart Planner combines task scheduling, to-do management, and budget tracking — all within a single application.\s
                                                          Whether you’re planning your day, tracking expenses, or managing monthly goals, our app ensures you stay in control of both your time and money.\s
                                                          We believe that success begins with smart planning — and we’re here to make that effortless for everyone.
                                                          </p>
                    
                                                          <h2>Our Mission</h2>
                                                          <p>
                                                          To empower individuals with smart tools that enhance productivity and promote financial awareness in everyday life.
                                                          </p>
                    
                                                          <h2>Our Vision</h2>
                                                          <p>
                                                          To become a trusted platform for personal productivity and financial management, helping users achieve better organization and smarter financial decisions.
                                                          </p>
                    
                                                          <h2>What We Offer</h2>
                                                          <ul>
                                                          <li>Task scheduling and to-do management with smart reminders</li>
                                                          <li>Budget tracking with income and expense categorization</li>
                                                          <li>Insights into spending habits and financial patterns</li>
                                                          <li>Upcoming productivity analytics and personalized tips</li>
                                                          <li>Simple, secure, and user-friendly experience</li>
                                                          </ul>
                    
                                                          <p>
                                                          We continuously work to improve GM Smart Planner and bring new features that enhance your daily productivity and financial control.\s
                                                          Your feedback helps us grow and serve you better.
                                                          </p>
                    
                                                          <h2>Contact Us</h2>
                    
                                                          <p>
                                                          🏢 <b>Company Name:</b> GM Technosys<br>
                    
                                                          📧 <b>Email:</b>
                                                          <a href="mailto:gmtechnosys7@gmail.com">
                                                          gmtechnosys7@gmail.com
                                                          </a><br>
                    
                                                          🌐 <b>Website:</b>
                                                          <a href="https://gmtechnosys.com/">
                                                          https://gmtechnosys.com/
                                                          </a><br>
                    
                                                          📍 <b>Address:</b>
                                                          Office No 719, 7th Floor,<br>
                                                          Satyamev Eminence, Near Shukan Mall,<br>
                                                          Science City Road, Sola,<br>
                                                          Ahmedabad – 380060
                                                          </p>
                    
                                                          </body>
            """;

    private static final String ABOUT_US_HI =
            """
                    <body>
                    
                                                          <p>
                                                          <b>GM Smart Planner</b> में आपका स्वागत है, जो आपके दैनिक जीवन को सरल बनाने के लिए डिज़ाइन किया गया एक अभिनव उत्पादकता और वित्तीय प्रबंधन (productivity and financial management) मोबाइल एप्लिकेशन है।\s
                                                          हम व्यक्तियों को व्यवस्थित, केंद्रित और वित्तीय रूप से जागरूक रहने में मदद करने के लिए समर्पित हैं।
                                                          </p>
                    
                                                          <p>
                                                          <b>GM Technosys</b> स्मार्ट और कुशल डिजिटल समाधान बनाने के लिए उत्साहित है।\s
                                                          हमारा लक्ष्य ऐसे उपकरण प्रदान करना है जो एक सहज मंच में उत्पादकता और वित्तीय अनुशासन को मूल रूप से जोड़ते हैं।
                                                          </p>
                    
                                                          <p>
                                                          फ़्लटर (Flutter) के साथ निर्मित, GM Smart Planner एक ही एप्लिकेशन के भीतर कार्य शेड्यूलिंग, टू-डू प्रबंधन और बजट ट्रैकिंग को जोड़ता है।\s
                                                          चाहे आप अपने दिन की योजना बना रहे हों, खर्चों पर नज़र रख रहे हों, या मासिक लक्ष्यों का प्रबंधन कर रहे हों, हमारा ऐप यह सुनिश्चित करता है कि आपका अपने समय और धन दोनों पर नियंत्रण रहे।\s
                                                          हमारा मानना है कि सफलता की शुरुआत स्मार्ट प्लानिंग से होती है - और हम इसे सभी के लिए आसान बनाने के लिए यहां हैं।
                                                          </p>
                    
                                                          <h2>हमारा मिशन</h2>
                                                          <p>
                                                          व्यक्तियों को ऐसे स्मार्ट उपकरणों के साथ सशक्त बनाना जो दैनिक जीवन में उत्पादकता बढ़ाते हैं और वित्तीय जागरूकता को बढ़ावा देते हैं।
                                                          </p>
                    
                                                          <h2>हमारा विज़न</h2>
                                                          <p>
                                                          व्यक्तिगत उत्पादकता और वित्तीय प्रबंधन के लिए एक विश्वसनीय मंच बनना, उपयोगकर्ताओं को बेहतर संगठन और स्मार्ट वित्तीय निर्णय लेने में मदद करना।
                                                          </p>
                    
                                                          <h2>हम क्या प्रदान करते हैं</h2>
                                                          <ul>
                                                          <li>स्मार्ट रिमाइंडर के साथ टास्क शेड्यूलिंग और टू-डू प्रबंधन</li>
                                                          <li>आय और व्यय वर्गीकरण के साथ बजट ट्रैकिंग</li>
                                                          <li>खर्च करने की आदतों और वित्तीय पैटर्न की जानकारी</li>
                                                          <li>आगामी उत्पादकता एनालिटिक्स और व्यक्तिगत सुझाव</li>
                                                          <li>सरल, सुरक्षित और उपयोगकर्ता के अनुकूल अनुभव</li>
                                                          </ul>
                    
                                                          <p>
                                                          हम GM Smart Planner को बेहतर बनाने और आपकी दैनिक उत्पादकता और वित्तीय नियंत्रण को बढ़ाने वाली नई सुविधाएँ लाने के लिए लगातार काम करते हैं।\s
                                                          आपकी प्रतिक्रिया हमें बढ़ने और आपको बेहतर सेवा देने में मदद करती है।
                                                          </p>
                    
                                                          <h2>हमसे संपर्क करें</h2>
                    
                                                          <p>
                                                          🏢 <b>कंपनी का नाम:</b> GM Technosys<br>
                    
                                                          📧 <b>ईमेल:</b>
                                                          <a href="mailto:gmtechnosys7@gmail.com">
                                                          gmtechnosys7@gmail.com
                                                          </a><br>
                    
                                                          🌐 <b>वेबसाइट:</b>
                                                          <a href="https://gmtechnosys.com/">
                                                          https://gmtechnosys.com/
                                                          </a><br>
                    
                                                          📍 <b>पता:</b>
                                                          ऑफिस नं 719, 7वीं मंजिल,<br>
                                                          सत्यमेव एमिनेंस, शुकन मॉल के पास,<br>
                                                          साइंस सिटी रोड, सोला,<br>
                                                          अहमदाबाद – 380060
                                                          </p>
                    
                                                          </body>
            """;

    private static final String ABOUT_US_GU =
            """
                    
                                                          <p>
                                                          <b>GM Smart Planner</b> માં તમારું સ્વાગત છે, જે તમારા રોજિંદા જીવનને સરળ બનાવવા માટે રચાયેલ એક નવીન ઉત્પાદકતા અને નાણાકીય વ્યવસ્થાપન (productivity and financial management) મોબાઇલ એપ્લિકેશન છે.\s
                                                          અમે વ્યક્તિઓને વ્યવસ્થિત, કેન્દ્રિત અને નાણાકીય રીતે જાગૃત રહેવામાં મદદ કરવા માટે સમર્પિત છીએ.
                                                          </p>
                    
                                                          <p>
                                                          <b>GM Technosys</b> સ્માર્ટ અને કાર્યક્ષમ ડિજિટલ ઉકેલો બનાવવા માટે ઉત્સાહી છે.\s
                                                          અમારું લક્ષ્ય એવા સાધનો પ્રદાન કરવાનું છે જે એક સાહજિક પ્લેટફોર્મમાં ઉત્પાદકતા અને નાણાકીય શિસ્તને એકીકૃત રીતે જોડે છે.
                                                          </p>
                    
                                                          <p>
                                                          ફ્લટર (Flutter) સાથે બનેલ, GM Smart Planner એક જ એપ્લિકેશનમાં ટાસ્ક શેડ્યુલિંગ, ટુ-ડુ મેનેજમેન્ટ અને બજેટ ટ્રેકિંગને જોડે છે.\s
                                                          પછી ભલે તમે તમારા દિવસનું આયોજન કરી રહ્યાં હોવ, ખર્ચ પર નજર રાખી રહ્યાં હોવ અથવા માસિક ધ્યેયોનું સંચાલન કરી રહ્યાં હોવ, અમારી ઍપ ખાતરી કરે છે કે તમે તમારા સમય અને નાણાં બંને પર નિયંત્રણ રાખો.\s
                                                          અમારું માનવું છે કે સફળતાની શરૂઆત સ્માર્ટ પ્લાનિંગથી થાય છે - અને અમે દરેક માટે તેને સરળ બનાવવા માટે અહીં છીએ.
                                                          </p>
                    
                                                          <h2>અમારું મિશન</h2>
                                                          <p>
                                                          વ્યક્તિઓને એવા સ્માર્ટ સાધનો વડે સશક્ત બનાવવા જે રોજિંદા જીવનમાં ઉત્પાદકતામાં વધારો કરે છે અને નાણાકીય જાગૃતિને પ્રોત્સાહન આપે છે.
                                                          </p>
                    
                                                          <h2>અમારું વિઝન</h2>
                                                          <p>
                                                          વ્યક્તિગત ઉત્પાદકતા અને નાણાકીય વ્યવસ્થાપન માટે એક વિશ્વસનીય પ્લેટફોર્મ બનવું, વપરાશકર્તાઓને વધુ સારા સંગઠન અને સ્માર્ટ નાણાકીય નિર્ણયો લેવામાં મદદ કરવી.
                                                          </p>
                    
                                                          <h2>અમે શું ઑફર કરીએ છીએ</h2>
                                                          <ul>
                                                          <li>સ્માર્ટ રિમાઇન્ડર સાથે ટાસ્ક શેડ્યુલિંગ અને ટુ-ડુ મેનેજમેન્ટ</li>
                                                          <li>આવક અને ખર્ચ વર્ગીકરણ સાથે બજેટ ટ્રેકિંગ</li>
                                                          <li>ખર્ચની આદતો અને નાણાકીય પેટર્નની આંતરદૃષ્ટિ</li>
                                                          <li>આગામી ઉત્પાદકતા એનાલિટિક્સ અને વ્યક્તિગત ટિપ્સ</li>
                                                          <li>સરળ, સુરક્ષિત અને વપરાશકર્તા-મૈત્રીપૂર્ણ અનુભવ</li>
                                                          </ul>
                    
                                                          <p>
                                                          અમે GM Smart Planner ને બહેતર બનાવવા અને તમારી દૈનિક ઉત્પાદકતા અને નાણાકીય નિયંત્રણમાં વધારો કરતી નવી સુવિધાઓ લાવવા માટે સતત કામ કરીએ છીએ.\s
                                                          તમારો પ્રતિસાદ અમને વિકાસ કરવામાં અને તમને વધુ સારી સેવા આપવામાં મદદ કરે છે.
                                                          </p>
                    
                                                          <h2>અમારો સંપર્ક કરો</h2>
                    
                                                          <p>
                                                          🏢 <b>કંપનીનું નામ:</b> GM Technosys<br>
                    
                                                          📧 <b>ઇમેઇલ:</b>
                                                          <a href="mailto:gmtechnosys7@gmail.com">
                                                          gmtechnosys7@gmail.com
                                                          </a><br>
                    
                                                          🌐 <b>વેબસાઇટ:</b>
                                                          <a href="https://gmtechnosys.com/">
                                                          https://gmtechnosys.com/
                                                          </a><br>
                    
                                                          📍 <b>સરનામું:</b>
                                                          ઓફિસ નં 719, 7મો માળ,<br>
                                                          સત્યમેવ એમિનન્સ, શુકન મોલની પાસે,<br>
                                                          સાયન્સ સિટી રોડ, સોલા,<br>
                                                          અમદાવાદ – 380060
                                                          </p>
                    
                                                          </body>
            """;

    private static final String PRIVACY_EN =
            """
                    <body>
                    
                                                                <p>
                                                                <b>GM Technosys</b> ("we", "our", or "us") operates the <b>GM Smart Planner</b> mobile application.
                                                                This Privacy Policy explains how we collect, use, and protect your personal information while using our application.
                                                                </p>
                    
                                                                <p>
                                                                <b>Effective Date:</b> October 30, 2025
                                                                </p>
                    
                                                                <p>
                                                                By using our app, you agree to the collection and use of information in accordance with this Privacy Policy.
                                                                </p>
                    
                                                                <h2>1. Information We Collect</h2>
                    
                                                                <p>We may collect the following types of information:</p>
                    
                                                                <h3>1.1 Personal Information</h3>
                                                                <ul>
                                                                <li>Name</li>
                                                                <li>Email Address</li>
                                                                <li>Any information provided during account registration</li>
                                                                </ul>
                    
                                                                <h3>1.2 Usage Data</h3>
                                                                <ul>
                                                                <li>App interactions</li>
                                                                <li>Time spent on features</li>
                                                                <li>Performance analytics to improve user experience</li>
                                                                </ul>
                    
                                                                <h3>1.3 Financial or Task Data</h3>
                                                                <ul>
                                                                <li>Budgets</li>
                                                                <li>Expenses</li>
                                                                <li>Personal notes entered by users</li>
                                                                </ul>
                    
                                                                <h2>2. How We Use Your Information</h2>
                                                                <p>We use collected information to:</p>
                                                                <ul>
                                                                <li>Provide and maintain app functionality</li>
                                                                <li>Improve user experience and app performance</li>
                                                                <li>Respond to user support requests and feedback</li>
                                                                <li>Send important updates about features or policy changes (if applicable)</li>
                                                                </ul>
                    
                                                                <h2>3. Data Storage and Security</h2>
                                                                <p>
                                                                All user data is securely stored and protected against unauthorized access, alteration, or disclosure.
                                                                We implement appropriate encryption and authentication measures to safeguard sensitive information.
                                                                </p>
                    
                                                                <p>
                                                                We do not sell, rent, or trade your personal data to third parties.
                                                                </p>
                    
                                                                <h2>4. Data Sharing</h2>
                                                                <p>We do not share personal data except in the following cases:</p>
                                                                <ul>
                                                                <li>When required by law or government authorities</li>
                                                                <li>With trusted service providers assisting in app operations under strict confidentiality agreements</li>
                                                                </ul>
                    
                                                                <h2>5. User Control</h2>
                                                                <p>Users have the right to:</p>
                                                                <ul>
                                                                <li>View, edit, or delete personal data through app settings</li>
                                                                <li>Request complete data deletion by contacting us via email</li>
                                                                </ul>
                    
                                                                <h2>6. Third-Party Services</h2>
                                                                <p>
                                                                Our app may use third-party services such as Firebase for authentication and data storage.
                                                                We are not responsible for the privacy practices or content of third-party services.
                                                                </p>
                    
                                                                <h2>7. Children’s Privacy</h2>
                                                                <p>
                                                                GM Smart Planner is not intended for users under the age of 13.
                                                                We do not knowingly collect personal information from children.
                                                                If such data is identified, it will be deleted promptly.
                                                                </p>
                    
                                                                <h2>8. Policy Updates</h2>
                                                                <p>
                                                                We may update this Privacy Policy from time to time.
                                                                Users will be notified of significant changes through the app or via email.
                                                                Continued use of the app after updates constitutes acceptance of the revised policy.
                                                                </p>
                    
                                                                <h2>9. Contact Us</h2>
                    
                                                                <p>
                                                                🏢 <b>Company Name:</b> GM Technosys<br>
                    
                                                                📧 <b>Email:</b>
                                                                <a href="mailto:gmtechnosys7@gmail.com">
                                                                gmtechnosys7@gmail.com
                                                                </a><br>
                    
                                                                🌐 <b>Website:</b>
                                                                <a href="https://gmtechnosys.com/">
                                                                https://gmtechnosys.com/
                                                                </a><br>
                    
                                                                📍 <b>Address:</b>
                                                                Office No 719, 7th Floor,<br>
                                                                Satyamev Eminence, Near Shukan Mall,<br>
                                                                Science City Road, Sola,<br>
                                                                Ahmedabad – 380060
                                                                </p>
                    
                                                                </body>
            """;

    private static final String PRIVACY_HI =
            """
                    <body>
                    
                                                                 <p>
                                                                 <b>GM Technosys</b> ("हम", "हमारा", या "हमें") <b>GM Smart Planner</b> मोबाइल एप्लिकेशन का संचालन करता है।\s
                                                                 यह गोपनीयता नीति (Privacy Policy) बताती है कि हमारे एप्लिकेशन का उपयोग करते समय हम आपकी व्यक्तिगत जानकारी कैसे एकत्र करते हैं, उसका उपयोग करते हैं और उसे सुरक्षित रखते हैं।
                                                                 </p>
                    
                                                                 <p>
                                                                 <b>प्रभावी तिथि:</b> 30 अक्टूबर, 2025
                                                                 </p>
                    
                                                                 <p>
                                                                 हमारे ऐप का उपयोग करके, आप इस गोपनीयता नीति के अनुसार जानकारी के संग्रह और उपयोग के लिए सहमत होते हैं।
                                                                 </p>
                    
                                                                 <h2>1. हम जो जानकारी एकत्र करते हैं</h2>
                    
                                                                 <p>हम निम्नलिखित प्रकार की जानकारी एकत्र कर सकते हैं:</p>
                    
                                                                 <h3>1.1 व्यक्तिगत जानकारी</h3>
                                                                 <ul>
                                                                 <li>नाम</li>
                                                                 <li>ईमेल पता (Email Address)</li>
                                                                 <li>खाता पंजीकरण के दौरान प्रदान की गई कोई भी जानकारी</li>
                                                                 </ul>
                    
                                                                 <h3>1.2 उपयोग डेटा (Usage Data)</h3>
                                                                 <ul>
                                                                 <li>ऐप इंटरैक्शन</li>
                                                                 <li>सुविधाओं पर बिताया गया समय</li>
                                                                 <li>उपयोगकर्ता अनुभव को बेहतर बनाने के लिए प्रदर्शन विश्लेषण</li>
                                                                 </ul>
                    
                                                                 <h3>1.3 वित्तीय या कार्य डेटा</h3>
                                                                 <ul>
                                                                 <li>बजट</li>
                                                                 <li>खर्च</li>
                                                                 <li>उपयोगकर्ताओं द्वारा दर्ज किए गए व्यक्तिगत नोट्स</li>
                                                                 </ul>
                    
                                                                 <h2>2. हम आपकी जानकारी का उपयोग कैसे करते हैं</h2>
                                                                 <p>हम एकत्रित जानकारी का उपयोग निम्नलिखित के लिए करते हैं:</p>
                                                                 <ul>
                                                                 <li>ऐप की कार्यक्षमता प्रदान करने और बनाए रखने के लिए</li>
                                                                 <li>उपयोगकर्ता अनुभव और ऐप के प्रदर्शन को बेहतर बनाने के लिए</li>
                                                                 <li>उपयोगकर्ता सहायता अनुरोधों और फीडबैक का जवाब देने के लिए</li>
                                                                 <li>सुविधाओं या नीतिगत बदलावों (यदि लागू हो) के बारे में महत्वपूर्ण अपडेट भेजने के लिए</li>
                                                                 </ul>
                    
                                                                 <h2>3. डेटा संग्रहण और सुरक्षा</h2>
                                                                 <p>
                                                                 सभी उपयोगकर्ता डेटा सुरक्षित रूप से संग्रहीत किया जाता है और अनधिकृत पहुंच, परिवर्तन या प्रकटीकरण से सुरक्षित रखा जाता है।
                                                                 हम संवेदनशील जानकारी की सुरक्षा के लिए उचित एन्क्रिप्शन और प्रमाणीकरण उपायों को लागू करते हैं।
                                                                 </p>
                    
                                                                 <p>
                                                                 हम आपके व्यक्तिगत डेटा को तीसरे पक्ष को बेचते, किराए पर नहीं देते हैं या उसका व्यापार नहीं करते हैं।
                                                                 </p>
                    
                                                                 <h2>4. डेटा साझाकरण</h2>
                                                                 <p>हम निम्नलिखित मामलों को छोड़कर व्यक्तिगत डेटा साझा नहीं करते हैं:</p>
                                                                 <ul>
                                                                 <li>जब कानून या सरकारी अधिकारियों द्वारा आवश्यक हो</li>
                                                                 <li>सख्त गोपनीयता समझौतों के तहत ऐप संचालन में सहायता करने वाले विश्वसनीय सेवा प्रदाताओं के साथ</li>
                                                                 </ul>
                    
                                                                 <h2>5. उपयोगकर्ता नियंत्रण</h2>
                                                                 <p>उपयोगकर्ताओं को निम्नलिखित अधिकार हैं:</p>
                                                                 <ul>
                                                                 <li>ऐप सेटिंग्स के माध्यम से व्यक्तिगत डेटा देखना, संपादित करना या हटाना</li>
                                                                 <li>ईमेल के माध्यम से हमसे संपर्क करके पूर्ण डेटा हटाने का अनुरोध करना</li>
                                                                 </ul>
                    
                                                                 <h2>6. तृतीय-पक्ष सेवाएँ</h2>
                                                                 <p>
                                                                 हमारा ऐप प्रमाणीकरण और डेटा संग्रहण के लिए फायरबेस (Firebase) जैसी तृतीय-पक्ष सेवाओं का उपयोग कर सकता है।
                                                                 हम तृतीय-पक्ष सेवाओं की गोपनीयता प्रथाओं या सामग्री के लिए ज़िम्मेदार नहीं हैं।
                                                                 </p>
                    
                                                                 <h2>7. बच्चों की गोपनीयता</h2>
                                                                 <p>
                                                                 GM Smart Planner 13 वर्ष से कम उम्र के उपयोगकर्ताओं के लिए नहीं है।
                                                                 हम जानबूझकर बच्चों से व्यक्तिगत जानकारी एकत्र नहीं करते हैं।
                                                                 यदि ऐसे डेटा की पहचान की जाती है, तो उसे तुरंत हटा दिया जाएगा।
                                                                 </p>
                    
                                                                 <h2>8. नीति अपडेट</h2>
                                                                 <p>
                                                                 हम समय-समय पर इस गोपनीयता नीति को अपडेट कर सकते हैं।
                                                                 उपयोगकर्ताओं को ऐप या ईमेल के माध्यम से महत्वपूर्ण बदलावों के बारे में सूचित किया जाएगा।
                                                                 अपडेट के बाद ऐप का निरंतर उपयोग संशोधित नीति की स्वीकृति माना जाएगा।
                                                                 </p>
                    
                                                                 <h2>9. हमसे संपर्क करें</h2>
                    
                                                                 <p>
                                                                 🏢 <b>कंपनी का नाम:</b> GM Technosys<br>
                    
                                                                 📧 <b>ईमेल:</b>
                                                                 <a href="mailto:gmtechnosys7@gmail.com">
                                                                 gmtechnosys7@gmail.com
                                                                 </a><br>
                    
                                                                 🌐 <b>वेबसाइट:</b>
                                                                 <a href="https://gmtechnosys.com/">
                                                                 https://gmtechnosys.com/
                                                                 </a><br>
                    
                                                                 📍 <b>पता:</b>
                                                                 ऑफिस नं 719, 7वीं मंजिल,<br>
                                                                 सत्यमेव एमिनेंस, शुकन मॉल के पास,<br>
                                                                 साइंस सिटी रोड, सोला,<br>
                                                                 अहमदाबाद – 380060
                                                                 </p>
                    
                                                                 </body>
            """;

    private static final String PRIVACY_GU =
            """
                    <p>
                                                                <b>GM Technosys</b> ("અમે", "અમારું", અથવા "અમને") <b>GM Smart Planner</b> મોબાઇલ એપ્લિકેશનનું સંચાલન કરે છે.
                                                                આ ગોપનીયતા નીતિ (Privacy Policy) સમજાવે છે કે અમારી એપ્લિકેશનનો ઉપયોગ કરતી વખતે અમે તમારી વ્યક્તિગત માહિતી કેવી રીતે એકત્રિત કરીએ છીએ, તેનો ઉપયોગ કરીએ છીએ અને તેનું રક્ષણ કરીએ છીએ.
                                                                </p>
                    
                                                                <p>
                                                                <b>અમલીકરણ તારીખ:</b> 30 ઑક્ટોબર, 2025
                                                                </p>
                    
                                                                <p>
                                                                અમારી ઍપનો ઉપયોગ કરીને, તમે આ ગોપનીયતા નીતિ અનુસાર માહિતીના સંગ્રહ અને ઉપયોગ માટે સંમત થાઓ છો.
                                                                </p>
                    
                                                                <h2>1. અમે જે માહિતી એકત્રિત કરીએ છીએ</h2>
                    
                                                                <p>અમે નીચે મુજબની માહિતી એકત્રિત કરી શકીએ છીએ:</p>
                    
                                                                <h3>1.1 વ્યક્તિગત માહિતી</h3>
                                                                <ul>
                                                                <li>નામ</li>
                                                                <li>ઇમેઇલ સરનામું</li>
                                                                <li>એકાઉન્ટ રજીસ્ટ્રેશન દરમિયાન આપવામાં આવેલી કોઈપણ માહિતી</li>
                                                                </ul>
                    
                                                                <h3>1.2 વપરાશ ડેટા (Usage Data)</h3>
                                                                <ul>
                                                                <li>ઍપ સાથેની ક્રિયાપ્રતિક્રિયાઓ</li>
                                                                <li>સુવિધાઓ પર વિતાવેલો સમય</li>
                                                                <li>વપરાશકર્તા અનુભવને સુધારવા માટે પ્રદર્શન વિશ્લેષણ</li>
                                                                </ul>
                    
                                                                <h3>1.3 નાણાકીય અથવા કાર્ય ડેટા</h3>
                                                                <ul>
                                                                <li>બજેટ</li>
                                                                <li>ખર્ચ</li>
                                                                <li>વપરાશકર્તાઓ દ્વારા દાખલ કરાયેલ વ્યક્તિગત નોંધો</li>
                                                                </ul>
                    
                                                                <h2>2. અમે તમારી માહિતીનો ઉપયોગ કેવી રીતે કરીએ છીએ</h2>
                                                                <p>અમે એકત્રિત કરેલી માહિતીનો ઉપયોગ આ માટે કરીએ છીએ:</p>
                                                                <ul>
                                                                <li>ઍપની કાર્યક્ષમતા પ્રદાન કરવા અને જાળવી રાખવા</li>
                                                                <li>વપરાશકર્તા અનુભવ અને ઍપના પ્રદર્શનને સુધારવા</li>
                                                                <li>વપરાશકર્તાની સહાય વિનંતીઓ અને પ્રતિસાદનો જવાબ આપવા</li>
                                                                <li>સુવિધાઓ અથવા નીતિમાં ફેરફાર વિશે મહત્વપૂર્ણ અપડેટ્સ મોકલવા (જો લાગુ હોય તો)</li>
                                                                </ul>
                    
                                                                <h2>3. ડેટા સ્ટોરેજ અને સુરક્ષા</h2>
                                                                <p>
                                                                તમામ વપરાશકર્તા ડેટા સુરક્ષિત રીતે સંગ્રહિત છે અને અનધિકૃત ઍક્સેસ, ફેરફાર અથવા જાહેરાત સામે સુરક્ષિત છે.
                                                                અમે સંવેદનશીલ માહિતીને સુરક્ષિત રાખવા માટે યોગ્ય એન્ક્રિપ્શન અને પ્રમાણીકરણના પગલાં અમલમાં મૂકીએ છીએ.
                                                                </p>
                    
                                                                <p>
                                                                અમે તમારા વ્યક્તિગત ડેટાને તૃતીય પક્ષોને વેચતા, ભાડે આપતા કે તેનો વેપાર કરતા નથી.
                                                                </p>
                    
                                                                <h2>4. ડેટા શેરિંગ</h2>
                                                                <p>અમે નીચેના કિસ્સાઓ સિવાય વ્યક્તિગત ડેટા શેર કરતા નથી:</p>
                                                                <ul>
                                                                <li>જ્યારે કાયદા દ્વારા અથવા સરકારી સત્તાવાળાઓ દ્વારા જરૂરી હોય</li>
                                                                <li>કડક ગોપનીયતા કરારો હેઠળ ઍપની કામગીરીમાં સહાય કરતા વિશ્વસનીય સેવા પ્રદાતાઓ સાથે</li>
                                                                </ul>
                    
                                                                <h2>5. વપરાશકર્તા નિયંત્રણ</h2>
                                                                <p>વપરાશકર્તાઓને આના અધિકાર છે:</p>
                                                                <ul>
                                                                <li>ઍપ સેટિંગ્સ દ્વારા વ્યક્તિગત ડેટા જોવાનો, તેમાં ફેરફાર કરવાનો કે ડિલીટ કરવાનો</li>
                                                                <li>ઇમેઇલ દ્વારા અમારો સંપર્ક કરીને સંપૂર્ણ ડેટા ડિલીટ કરવાની વિનંતી કરવાનો</li>
                                                                </ul>
                    
                                                                <h2>6. થર્ડ-પાર્ટી સેવાઓ</h2>
                                                                <p>
                                                                અમારી ઍપ પ્રમાણીકરણ અને ડેટા સંગ્રહ માટે ફાયરબેઝ (Firebase) જેવી તૃતીય-પક્ષ સેવાઓનો ઉપયોગ કરી શકે છે.
                                                                અમે તૃતીય-પક્ષ સેવાઓની ગોપનીયતા પ્રથાઓ અથવા સામગ્રી માટે જવાબદાર નથી.
                                                                </p>
                    
                                                                <h2>7. બાળકોની ગોપનીયતા</h2>
                                                                <p>
                                                                GM Smart Planner 13 વર્ષથી ઓછી ઉંમરના વપરાશકર્તાઓ માટે નથી.
                                                                અમે જાણીજોઈને બાળકો પાસેથી વ્યક્તિગત માહિતી એકત્રિત કરતા નથી.
                                                                જો આવા ડેટાની ઓળખ થશે, તો તેને તરત જ ડિલીટ કરવામાં આવશે.
                                                                </p>
                    
                                                                <h2>8. નીતિ અપડેટ્સ</h2>
                                                                <p>
                                                                અમે સમયાંતરે આ ગોપનીયતા નીતિને અપડેટ કરી શકીએ છીએ.
                                                                વપરાશકર્તાઓને ઍપ અથવા ઇમેઇલ દ્વારા નોંધપાત્ર ફેરફારો વિશે જાણ કરવામાં આવશે.
                                                                અપડેટ્સ પછી ઍપનો સતત ઉપયોગ એ સુધારેલી નીતિની સ્વીકૃતિ ગણાશે.
                                                                </p>
                    
                                                                <h2>9. અમારો સંપર્ક કરો</h2>
                    
                                                                <p>
                                                                🏢 <b>કંપનીનું નામ:</b> GM Technosys<br>
                    
                                                                📧 <b>ઇમેઇલ:</b>
                                                                <a href="mailto:gmtechnosys7@gmail.com">
                                                                gmtechnosys7@gmail.com
                                                                </a><br>
                    
                                                                🌐 <b>વેબસાઇટ:</b>
                                                                <a href="https://gmtechnosys.com/">
                                                                https://gmtechnosys.com/
                                                                </a><br>
                    
                                                                📍 <b>સરનામું:</b>
                                                                ઓફિસ નં 719, 7મો માળ,<br>
                                                                સત્યમેવ એમિનન્સ, શુકન મોલની પાસે,<br>
                                                                સાયન્સ સિટી રોડ, સોલા,<br>
                                                                અમદાવાદ – 380060
                                                                </p>
                    
                                                                </body>
            """;

    private static final String TERMS_EN =
            """
                    <body>
                    
                                                        <p>
                                                        <b>GM Technosys</b> ("we", "our", or "us") operates the <b>GM Smart Planner</b> mobile application.
                                                        These Terms and Conditions govern your use of our application.
                                                        </p>
                    
                                                        <p>
                                                        <b>Effective Date:</b> October 30, 2025
                                                        </p>
                    
                                                        <p>
                                                        By downloading, accessing, or using our app, you agree to comply with and be bound by these Terms and Conditions.
                                                        If you do not agree, please discontinue use of the app immediately.
                                                        </p>
                    
                                                        <h2>1. Acceptance of Terms</h2>
                                                        <p>
                                                        By accessing or using GM Smart Planner, you acknowledge that you have read, understood,
                                                        and agree to be bound by these Terms and Conditions and our Privacy Policy.
                                                        </p>
                    
                                                        <h2>2. Use of the Application</h2>
                                                        <p>You agree to use the app for lawful purposes only. You must not:</p>
                                                        <ul>
                                                        <li>Misuse, hack, or attempt to exploit any part of the application</li>
                                                        <li>Engage in unauthorized or illegal activities</li>
                                                        <li>Resell, reproduce, or redistribute app features or services</li>
                                                        </ul>
                    
                                                        <p>
                                                        All services are provided for personal and non-commercial use only.
                                                        We reserve the right to suspend or terminate access if misuse is detected.
                                                        </p>
                    
                                                        <h2>3. Account and Data Responsibility</h2>
                                                        <ul>
                                                        <li>You are responsible for maintaining the confidentiality of your login credentials</li>
                                                        <li>You are responsible for all data entered into the app (tasks, budgets, expenses, etc.)</li>
                                                        <li>We do not access, view, or share your personal data without your consent</li>
                                                        <li>You are responsible for ensuring your data is accurate and properly backed up</li>
                                                        </ul>
                    
                                                        <h2>4. Intellectual Property Rights</h2>
                                                        <p>
                                                        All content, features, design elements, source code, logos, and trademarks are the exclusive property of GM Technosys
                                                        and are protected under applicable copyright and trademark laws.
                                                        </p>
                    
                                                        <p>
                                                        Unauthorized reproduction, modification, or distribution of any part of the app is strictly prohibited.
                                                        </p>
                    
                                                        <h2>5. Limitation of Liability</h2>
                                                        <p>
                                                        The app is provided on an "as-is" and "as-available" basis without warranties of any kind.
                                                        </p>
                    
                                                        <ul>
                                                        <li>We are not responsible for direct or indirect damages arising from app usage</li>
                                                        <li>We are not liable for data loss or system failures</li>
                                                        <li>We are not responsible for third-party service interruptions</li>
                                                        </ul>
                    
                                                        <h2>6. Updates and Modifications</h2>
                                                        <p>
                                                        We may update or modify these Terms at any time without prior notice.
                                                        Continued use of the app after changes means you accept the revised Terms.
                                                        </p>
                    
                                                        <h2>7. Termination</h2>
                                                        <p>
                                                        We reserve the right to suspend or terminate access to the app at any time
                                                        for violations of these Terms or for security and operational reasons.
                                                        </p>
                    
                                                        <h2>8. Governing Law</h2>
                                                        <p>
                                                        These Terms are governed by the laws of India.
                                                        Any disputes shall fall under the exclusive jurisdiction of the courts in Ahmedabad, Gujarat.
                                                        </p>
                    
                                                        <h2>9. Contact Us</h2>
                    
                                                        <p>
                                                        🏢 <b>Company Name:</b> GM Technosys<br>
                    
                                                        📧 <b>Email:</b>
                                                        <a href="mailto:gmtechnosys7@gmail.com">
                                                        gmtechnosys7@gmail.com
                                                        </a><br>
                    
                                                        🌐 <b>Website:</b>
                                                        <a href="https://gmtechnosys.com/">
                                                        https://gmtechnosys.com/
                                                        </a><br>
                    
                                                        📍 <b>Address:</b>
                                                        Office No 719, 7th Floor,<br>
                                                        Satyamev Eminence, Near Shukan Mall,<br>
                                                        Science City Road, Sola,<br>
                                                        Ahmedabad – 380060
                                                        </p>
                    
                                                        </body>
            """;

    private static final String TERMS_HI =
            """
                    <p>
                                                         <b>GM Technosys</b> ("हम", "हमारा", या "हमें") <b>GM Smart Planner</b> मोबाइल एप्लिकेशन का संचालन करता है।
                                                         ये नियम और शर्तें (Terms and Conditions) हमारे एप्लिकेशन के आपके उपयोग को नियंत्रित करती हैं।
                                                         </p>
                    
                                                         <p>
                                                         <b>प्रभावी तिथि:</b> 30 अक्टूबर, 2025
                                                         </p>
                    
                                                         <p>
                                                         हमारे ऐप को डाउनलोड करने, एक्सेस करने या उपयोग करने से, आप इन नियमों और शर्तों का पालन करने और उनसे बाध्य होने के लिए सहमत होते हैं।
                                                         यदि आप सहमत नहीं हैं, तो कृपया तुरंत ऐप का उपयोग बंद कर दें।
                                                         </p>
                    
                                                         <h2>1. शर्तों की स्वीकृति</h2>
                                                         <p>
                                                         GM Smart Planner को एक्सेस या उपयोग करके, आप स्वीकार करते हैं कि आपने इन नियमों और शर्तों तथा हमारी गोपनीयता नीति को पढ़ और समझ लिया है,
                                                         और आप इनसे बाध्य होने के लिए सहमत हैं।
                                                         </p>
                    
                                                         <h2>2. एप्लिकेशन का उपयोग</h2>
                                                         <p>आप ऐप का उपयोग केवल वैध उद्देश्यों के लिए करने के लिए सहमत हैं। आपको यह नहीं करना चाहिए:</p>
                                                         <ul>
                                                         <li>एप्लिकेशन के किसी भी हिस्से का दुरुपयोग, हैक, या शोषण करने का प्रयास</li>
                                                         <li>अनधिकृत या अवैध गतिविधियों में शामिल होना</li>
                                                         <li>ऐप की सुविधाओं या सेवाओं को फिर से बेचना, पुनरुत्पादित करना या पुनर्वितरित करना</li>
                                                         </ul>
                    
                                                         <p>
                                                         सभी सेवाएं केवल व्यक्तिगत और गैर-व्यावसायिक उपयोग के लिए प्रदान की जाती हैं।
                                                         यदि दुरुपयोग का पता चलता है तो हम एक्सेस को निलंबित या समाप्त करने का अधिकार सुरक्षित रखते हैं।
                                                         </p>
                    
                                                         <h2>3. खाता और डेटा जिम्मेदारी</h2>
                                                         <ul>
                                                         <li>आप अपने लॉगिन क्रेडेंशियल की गोपनीयता बनाए रखने के लिए जिम्मेदार हैं</li>
                                                         <li>आप ऐप में दर्ज किए गए सभी डेटा (कार्य, बजट, खर्च आदि) के लिए जिम्मेदार हैं</li>
                                                         <li>हम आपकी सहमति के बिना आपके व्यक्तिगत डेटा तक नहीं पहुंचते, देखते या साझा नहीं करते हैं</li>
                                                         <li>आप यह सुनिश्चित करने के लिए जिम्मेदार हैं कि आपका डेटा सटीक है और उसका उचित बैकअप लिया गया है</li>
                                                         </ul>
                    
                                                         <h2>4. बौद्धिक संपदा अधिकार</h2>
                                                         <p>
                                                         सभी सामग्री, सुविधाएँ, डिज़ाइन तत्व, स्रोत कोड, लोगो और ट्रेडमार्क GM Technosys की विशेष संपत्ति हैं
                                                         और लागू कॉपीराइट और ट्रेडमार्क कानूनों के तहत संरक्षित हैं।
                                                         </p>
                    
                                                         <p>
                                                         ऐप के किसी भी हिस्से का अनधिकृत पुनरुत्पादन, संशोधन या वितरण सख्त वर्जित है।
                                                         </p>
                    
                                                         <h2>5. देयता की सीमा (Limitation of Liability)</h2>
                                                         <p>
                                                         ऐप "जैसा है" और "जैसा उपलब्ध है" के आधार पर बिना किसी प्रकार की वारंटी के प्रदान किया जाता है।
                                                         </p>
                    
                                                         <ul>
                                                         <li>हम ऐप के उपयोग से होने वाले प्रत्यक्ष या अप्रत्यक्ष नुकसान के लिए ज़िम्मेदार नहीं हैं</li>
                                                         <li>हम डेटा हानि या सिस्टम विफलताओं के लिए उत्तरदायी नहीं हैं</li>
                                                         <li>हम तृतीय-पक्ष सेवा रुकावटों के लिए ज़िम्मेदार नहीं हैं</li>
                                                         </ul>
                    
                                                         <h2>6. अपडेट और संशोधन</h2>
                                                         <p>
                                                         हम बिना किसी पूर्व सूचना के किसी भी समय इन शर्तों को अपडेट या संशोधित कर सकते हैं।
                                                         परिवर्तनों के बाद ऐप का निरंतर उपयोग करने का अर्थ है कि आप संशोधित शर्तों को स्वीकार करते हैं।
                                                         </p>
                    
                                                         <h2>7. समाप्ति (Termination)</h2>
                                                         <p>
                                                         हम इन शर्तों के उल्लंघन के लिए या सुरक्षा और परिचालन कारणों से किसी भी समय ऐप तक पहुंच को
                                                         निलंबित या समाप्त करने का अधिकार सुरक्षित रखते हैं।
                                                         </p>
                    
                                                         <h2>8. शासी कानून (Governing Law)</h2>
                                                         <p>
                                                         ये शर्तें भारत के कानूनों द्वारा शासित हैं।
                                                         कोई भी विवाद अहमदाबाद, गुजरात के न्यायालयों के विशेष अधिकार क्षेत्र के अंतर्गत आएगा।
                                                         </p>
                    
                                                         <h2>9. हमसे संपर्क करें</h2>
                    
                                                         <p>
                                                         🏢 <b>कंपनी का नाम:</b> GM Technosys<br>
                    
                                                         📧 <b>ईमेल:</b>
                                                         <a href="mailto:gmtechnosys7@gmail.com">
                                                         gmtechnosys7@gmail.com
                                                         </a><br>
                    
                                                         🌐 <b>वेबसाइट:</b>
                                                         <a href="https://gmtechnosys.com/">
                                                         https://gmtechnosys.com/
                                                         </a><br>
                    
                                                         📍 <b>पता:</b>
                                                         ऑफिस नं 719, 7वीं मंजिल,<br>
                                                         सत्यमेव एमिनेंस, शुकन मॉल के पास,<br>
                                                         साइंस सिटी रोड, सोला,<br>
                                                         अहमदाबाद – 380060
                                                         </p>
                    
                                                         </body>
            """;

    private static final String TERMS_GU =
            """
                    <p>
                                                         <b>GM Technosys</b> ("અમે", "અમારું", અથવા "અમને") <b>GM Smart Planner</b> મોબાઇલ એપ્લિકેશનનું સંચાલન કરે છે.
                                                         આ નિયમો અને શરતો (Terms and Conditions) અમારી એપ્લિકેશનના તમારા ઉપયોગને નિયંત્રિત કરે છે.
                                                         </p>
                    
                                                         <p>
                                                         <b>અમલીકરણ તારીખ:</b> 30 ઑક્ટોબર, 2025
                                                         </p>
                    
                                                         <p>
                                                         અમારી ઍપ ડાઉનલોડ કરીને, ઍક્સેસ કરીને અથવા તેનો ઉપયોગ કરીને, તમે આ નિયમો અને શરતોનું પાલન કરવા અને તેનાથી બંધાયેલા રહેવા માટે સંમત થાઓ છો.
                                                         જો તમે સંમત નથી, તો કૃપા કરીને ઍપનો ઉપયોગ તાત્કાલિક બંધ કરો.
                                                         </p>
                    
                                                         <h2>1. શરતોની સ્વીકૃતિ</h2>
                                                         <p>
                                                         GM Smart Planner ને ઍક્સેસ કરીને અથવા તેનો ઉપયોગ કરીને, તમે સ્વીકારો છો કે તમે આ નિયમો અને શરતો અને અમારી ગોપનીયતા નીતિ વાંચી, સમજી છે,
                                                         અને તમે તેનાથી બંધાયેલા રહેવા માટે સંમત થાઓ છો.
                                                         </p>
                    
                                                         <h2>2. એપ્લિકેશનનો ઉપયોગ</h2>
                                                         <p>તમે માત્ર કાયદેસરના હેતુઓ માટે જ ઍપનો ઉપયોગ કરવા માટે સંમત થાઓ છો. તમારે આ ન કરવું જોઈએ:</p>
                                                         <ul>
                                                         <li>એપ્લિકેશનના કોઈપણ ભાગનો દુરુપયોગ, હૅક, અથવા શોષણ કરવાનો પ્રયાસ</li>
                                                         <li>અનધિકૃત અથવા ગેરકાયદેસર પ્રવૃત્તિઓમાં સામેલ થવું</li>
                                                         <li>ઍપની સુવિધાઓ અથવા સેવાઓનું પુનઃવેચાણ, પુનરુત્પાદન અથવા પુનર્વિતરણ કરવું</li>
                                                         </ul>
                    
                                                         <p>
                                                         તમામ સેવાઓ માત્ર વ્યક્તિગત અને બિન-વ્યાવસાયિક ઉપયોગ માટે પ્રદાન કરવામાં આવે છે.
                                                         જો દુરુપયોગ જણાશે તો અમે ઍક્સેસને સ્થગિત અથવા સમાપ્ત કરવાનો અધિકાર સુરક્ષિત રાખીએ છીએ.
                                                         </p>
                    
                                                         <h2>3. એકાઉન્ટ અને ડેટાની જવાબદારી</h2>
                                                         <ul>
                                                         <li>તમે તમારા લોગિન ક્રેડેન્શિયલ્સની ગોપનીયતા જાળવવા માટે જવાબદાર છો</li>
                                                         <li>તમે ઍપમાં દાખલ કરેલા તમામ ડેટા (કાર્યો, બજેટ, ખર્ચ વગેરે) માટે જવાબદાર છો</li>
                                                         <li>અમે તમારી સંમતિ વિના તમારા વ્યક્તિગત ડેટાને ઍક્સેસ કરતા નથી, જોતા નથી અથવા શેર કરતા નથી</li>
                                                         <li>તમારો ડેટા સચોટ છે અને તેનું યોગ્ય બૅકઅપ લેવામાં આવ્યું છે તેની ખાતરી કરવા માટે તમે જવાબદાર છો</li>
                                                         </ul>
                    
                                                         <h2>4. બૌદ્ધિક સંપદા અધિકારો</h2>
                                                         <p>
                                                         તમામ સામગ્રી, સુવિધાઓ, ડિઝાઇન તત્વો, સોર્સ કોડ, લોગો અને ટ્રેડમાર્ક એ GM Technosys ની વિશિષ્ટ સંપત્તિ છે
                                                         અને લાગુ કોપીરાઇટ અને ટ્રેડમાર્ક કાયદા હેઠળ સુરક્ષિત છે.
                                                         </p>
                    
                                                         <p>
                                                         ઍપના કોઈપણ ભાગનું અનધિકૃત પુનરુત્પાદન, ફેરફાર અથવા વિતરણ સખત પ્રતિબંધિત છે.
                                                         </p>
                    
                                                         <h2>5. જવાબદારીની મર્યાદા (Limitation of Liability)</h2>
                                                         <p>
                                                         ઍપ કોઈપણ પ્રકારની વોરંટી વિના "જેમ છે" અને "જેમ ઉપલબ્ધ છે" ના આધારે પ્રદાન કરવામાં આવે છે.
                                                         </p>
                    
                                                         <ul>
                                                         <li>અમે ઍપના ઉપયોગથી થતા પ્રત્યક્ષ અથવા પરોક્ષ નુકસાન માટે જવાબદાર નથી</li>
                                                         <li>અમે ડેટાની ખોટ અથવા સિસ્ટમની નિષ્ફળતાઓ માટે જવાબદાર નથી</li>
                                                         <li>અમે થર્ડ-પાર્ટી સેવામાં આવતા વિક્ષેપો માટે જવાબદાર નથી</li>
                                                         </ul>
                    
                                                         <h2>6. અપડેટ્સ અને સુધારાઓ</h2>
                                                         <p>
                                                         અમે કોઈપણ પૂર્વ સૂચના વિના કોઈપણ સમયે આ શરતોને અપડેટ અથવા સંશોધિત કરી શકીએ છીએ.
                                                         ફેરફારો પછી ઍપનો સતત ઉપયોગ કરવાનો અર્થ એ છે કે તમે સુધારેલી શરતો સ્વીકારો છો.
                                                         </p>
                    
                                                         <h2>7. સમાપ્તિ (Termination)</h2>
                                                         <p>
                                                         આ શરતોના ઉલ્લંઘન માટે અથવા સુરક્ષા અને ઓપરેશનલ કારણોસર અમે કોઈપણ સમયે ઍપની ઍક્સેસને
                                                         સ્થગિત અથવા સમાપ્ત કરવાનો અધિકાર સુરક્ષિત રાખીએ છીએ.
                                                         </p>
                    
                                                         <h2>8. સંચાલક કાયદો (Governing Law)</h2>
                                                         <p>
                                                         આ શરતો ભારતના કાયદાઓ દ્વારા સંચાલિત છે.
                                                         કોઈપણ વિવાદ અમદાવાદ, ગુજરાતની અદાલતોના વિશેષ અધિકારક્ષેત્ર હેઠળ આવશે.
                                                         </p>
                    
                                                         <h2>9. અમારો સંપર્ક કરો</h2>
                    
                                                         <p>
                                                         🏢 <b>કંપનીનું નામ:</b> GM Technosys<br>
                    
                                                         📧 <b>ઇમેઇલ:</b>
                                                         <a href="mailto:gmtechnosys7@gmail.com">
                                                         gmtechnosys7@gmail.com
                                                         </a><br>
                    
                                                         🌐 <b>વેબસાઇટ:</b>
                                                         <a href="https://gmtechnosys.com/">
                                                         https://gmtechnosys.com/
                                                         </a><br>
                    
                                                         📍 <b>સરનામું:</b>
                                                         ઓફિસ નં 719, 7મો માળ,<br>
                                                         સત્યમેવ એમિનન્સ, શુકન મોલની પાસે,<br>
                                                         સાયન્સ સિટી રોડ, સોલા,<br>
                                                         અમદાવાદ – 380060
                                                         </p>
                    
                                                         </body>
            """;
}