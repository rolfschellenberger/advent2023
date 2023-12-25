#!/usr/bin/python
from fractions import Fraction as F

data = [
    ((225415405941969, 400648127977931, 79201130433258), (23, -204, 617)),
    ((353783687623292, 138575899489956, 318416438572569), (-80, 156, 21)),
    ((215751176267772, 376619563956940, 230133299986253), (-120, 126, -352)),
    ((323665001411559, 234169007288514, 312785643963696), (21, -57, 87)),
    ((254448034578908, 463170080025786, 244090582025017), (-86, -444, -22)),
    ((273471653417187, 172046246078624, 48450798656249), (51, 61, 380)),
    ((287227040623943, 157690235374498, 384072710396003), (37, 71, -26)),
    ((196570909856452, 123834875604024, 344193708165349), (146, 78, 41)),
    ((291836802016516, 158629082612480, 342455569571405), (47, 31, 49)),
    ((298917064765569, 185466058651841, 449492757808548), (-51, 192, -291)),
    ((277447863776037, 223974644004974, 423175649399949), (49, -9, -72)),
    ((199169064813882, 445639406916578, 199189746431220), (80, -661, 25)),
    ((223236043139832, 224306682732104, 402909760074704), (66, 277, -395)),
    ((160873242048546, 2807347124660, 304979991957720), (208, 505, -7)),
    ((180322479461100, 363347387196216, 291367348962937), (168, -165, 59)),
    ((191508437104116, 143482518396184, 170693733091549), (156, 938, 266)),
    ((300262336776900, 288953165796224, 248618365141389), (13, -77, 131)),
    ((339372161410582, 485966647161393, 439531131447631), (-40, -330, -118)),
    ((240537427307140, 322136671432640, 249196664210477), (28, -11, 44)),
    ((249882429145512, 306189656212496, 240713646859308), (50, -51, 113)),
    ((77035247523111, 70939166181557, 234452997993258), (267, 112, 163)),
    ((207536104523712, 363405227575334, 221013673815459), (50, 67, -50)),
    ((261247833878290, 352514363170541, 302831453279296), (-112, -20, -247)),
    ((188444983635892, 315104348409984, 240766146469389), (196, 725, -396)),
    ((359796087574467, 111122874778900, 326659019395370), (-15, 65, 73)),
    ((178880471858808, 79854689532476, 21817450628085), (172, 287, 468)),
    ((232988926739376, 464839183237100, 261667422860736), (-62, -535, -205)),
    ((265926918569922, 267256078421696, 311633035119111), (-85, 221, -201)),
    ((261596124854016, 224998976566733, 77390405219169), (58, 16, 356)),
    ((257370294349506, 235548554231378, 271577006946924), (24, 107, 41)),
    ((377179387919364, 171297810997880, 333963867168141), (-78, 64, 28)),
    ((335525619653812, 276766669213904, 450741382665389), (-5, -86, -76)),
    ((233022922631022, 323546756912150, 73999021188171), (105, -134, 337)),
    ((197703129313946, 356721184961974, 324475431876400), (116, 123, -785)),
    ((299774046272417, 281391906480039, 324869409355059), (-61, 20, -66)),
    ((158456241300600, 328005645597014, 323351855821407), (196, -122, 27)),
    ((318317477826499, 205999790699555, 355971505043039), (13, -8, 26)),
    ((383867610317492, 305266672202336, 391644492022061), (-65, -113, -20)),
    ((267880895636388, 231892959289508, 223009170722289), (-42, 220, 111)),
    ((284944636544954, 81652494161206, 485157498035792), (44, 147, -133)),
    ((299445265390278, 183358851734814, 393326001570685), (33, 18, -16)),
    ((379352339948004, 272952661790348, 341575518626421), (-58, -78, 38)),
    ((238540184953467, 346812031824029, 230471715374112), (-45, 28, 9)),
    ((332988931413672, 150313594552640, 391171513453800), (10, 29, 7)),
    ((213113841492680, 329710299385166, 238864217596340), (82, 31, 25)),
    ((213825842422096, 374350857270145, 206903707715456), (-46, 74, -17)),
    ((232829244318041, 243969891662384, 251077640273511), (91, 13, 114)),
    ((296488159789348, 487393597839040, 375568882706669), (-32, -369, -124)),
    ((445689727525704, 261240829814024, 334067255421553), (-111, -77, 59)),
    ((310845126320652, 129182513470544, 380690205714669), (36, 41, 24)),
    ((139076042564018, 235908097774312, 238553211471622), (208, -43, 155)),
    ((206569666655868, 445187859332993, 258701719850118), (78, -430, -187)),
    ((381169276760890, 268553967611230, 356567213426095), (-97, -46, -13)),
    ((196682799381384, 424850860356476, 229135490685093), (121, -366, -142)),
    ((218508854719362, 406255246247474, 196295487832399), (-117, -224, 67)),
    ((314526420745230, 232426655834918, 289805146474341), (-17, 12, 68)),
    ((254949801045000, 402044374105564, 325952697218669), (12, -217, -104)),
    ((311446485053989, 419259306267835, 279275375951782), (-251, -270, -113)),
    ((318816250869339, 170041734451751, 266254607710398), (11, 34, 123)),
    ((401648999679771, 377458817972572, 506069231423157), (-51, -199, -96)),
    ((336971252079632, 107586440480344, 437081903195049), (-11, 107, -69)),
    ((256752413412028, 323623430186616, 253009610659687), (-38, 17, 7)),
    ((276448713332340, 291613396696898, 332902084523969), (6, -29, -44)),
    ((217049764421508, 16535968312676, 188030526475173), (126, 160, 210)),
    ((430636006058020, 430145841022952, 272022845023541), (-122, -254, 113)),
    ((278658890419912, 143145340017624, 374887457383649), (-7, 255, -137)),
    ((210262838736447, 174243721261669, 111164549783989), (93, 534, 448)),
    ((143154653879978, 200787535108982, 297131676698967), (229, 100, 33)),
    ((242371553144156, 345175701259536, 157135629379533), (-113, 99, 348)),
    ((338306775387030, 270004585934852, 258168599702587), (-144, 49, 62)),
    ((230277192759036, 494976337883408, 249442359408865), (-114, -851, -260)),
    ((312367260731079, 381530933151503, 363555874166457), (21, -200, 20)),
    ((326083884147444, 218739320608865, 394766845636539), (6, -24, -14)),
    ((236029304557482, 369306753687179, 259777655517429), (-32, -70, -112)),
    ((216574933928097, 398639506750529, 213892205510614), (69, -200, 107)),
    ((237335823078942, 213289885006070, 349362466181751), (95, 12, 9)),
    ((218041760875367, 399349540805494, 361110969428166), (115, -217, -33)),
    ((307049544359281, 187861911603083, 248136102967972), (-121, 292, 59)),
    ((307797917085471, 208109199721770, 201630850068889), (33, -25, 196)),
    ((226681413160374, 394033430727447, 145141355630953), (-96, -136, 484)),
    ((366362348501392, 110574360740164, 322824338552601), (-20, 63, 78)),
    ((278151629518337, 459081036445549, 347102188663199), (25, -304, -26)),
    ((439945736956707, 296728963533604, 545940314429821), (-165, -87, -248)),
    ((218169479639892, 380604520916402, 210453400010739), (-90, 19, -54)),
    ((150932586598524, 22178919522684, 286175262682301), (216, 378, 52)),
    ((331609247787080, 270253089584228, 360020790359569), (-76, -5, -74)),
    ((344749447646148, 192860376041816, 196130162488245), (-54, 60, 197)),
    ((273834833252711, 181231187099392, 171102343952527), (-103, 477, 250)),
    ((218958756477828, 178667207194304, 291879030866253), (79, 393, -83)),
    ((214695365376592, 376758165581977, 217071216531174), (10, -36, -9)),
    ((229144350000118, 270028100284076, 434721682455423), (32, 217, -606)),
    ((211480182096879, 134574407628080, 387849316745058), (81, 781, -547)),
    ((278176045017110, 286034303976134, 405786504742059), (-73, 88, -369)),
    ((391627162864740, 89763424201670, 419051266425510), (-42, 79, -13)),
    ((235546483584772, 459341134172384, 281510664738669), (-61, -488, -276)),
    ((197552291774709, 388398873511553, 231524570439426), (127, -138, -27)),
    ((189160741211220, 272988361966184, 236728241567445), (164, 278, 9)),
    ((283786104693088, 208848767085956, 16821295739877), (41, 10, 414)),
    ((439800390512868, 153631980121136, 448835318018445), (-98, 27, -52)),
    ((258090919190790, 283122430545397, 329065882810319), (48, -34, -14)),
    ((252971223586976, 160033199352084, 223856865067061), (61, 138, 154)),
    ((189273902703512, 340355704139076, 215005959999989), (200, 695, -244)),
    ((241018252401487, 379825143566794, 256857150416654), (-5, -141, -25)),
    ((237078959171127, 332722400772992, 287717100270624), (35, -35, -59)),
    ((210092124080574, 373583827396088, 377868626511030), (104, -139, -307)),
    ((222649220365092, 188733542582264, 329982904664409), (118, 7, 57)),
    ((200934204016904, 412751819742664, 119589271478307), (124, -247, 417)),
    ((262616147240112, 269130205434521, 364038942933588), (50, -30, -45)),
    ((326828884393380, 282520533128063, 285074636277990), (-106, 10, 19)),
    ((199814776055240, 316265119839263, 174300641838897), (74, 760, 300)),
    ((22796964231616, 24076714661136, 130891021959509), (383, 297, 286)),
    ((202808773120103, 256595618886852, 229416360707983), (137, -31, 154)),
    ((233723345016460, 463438695880617, 266554779303653), (-194, -706, -496)),
    ((241481762758014, 393761068901200, 206407280423827), (-63, -171, 109)),
    ((518127912760804, 498024083437178, 402831208464143), (-202, -326, -23)),
    ((240833519474520, 339675760554728, 428134336927485), (51, -89, -292)),
    ((305135973271500, 274119169687792, 345355095624095), (-6, -43, -11)),
    ((227654582290332, 339082170897884, 232049465174229), (-14, 91, -20)),
    ((259199091491824, 57407428495024, 382737082699859), (74, 173, -14)),
    ((452394237066988, 164075009432144, 494498712901581), (-139, 43, -132)),
    ((215464104299964, 373269929526578, 434673927473604), (78, -121, -589)),
    ((202984446281102, 437122311053364, 290086813979089), (103, -369, -286)),
    ((246051066582260, 207457013302672, 302655056355533), (-103, 720, -358)),
    ((186905545816068, 285155031819140, 255462338690125), (174, 262, -80)),
    ((338157704392412, 179157589001881, 242281095272762), (-50, 86, 133)),
    ((286172306159441, 132935988213280, 506520950787516), (-101, 509, -657)),
    ((216529375627444, 362883490693896, 126490932037805), (92, -120, 352)),
    ((431539904919440, 320298896032708, 385561040437867), (-88, -141, 13)),
    ((303837390389397, 337113428452898, 433525268453835), (-45, -105, -226)),
    ((185050526954304, 299552400224195, 353429922069219), (186, 266, -574)),
    ((249590979210178, 365147493699038, 226446168330971), (76, -173, 158)),
    ((454919821354383, 337365156114450, 488595942394388), (-297, -109, -307)),
    ((298342167647595, 286443649385279, 489021520472751), (-153, 118, -666)),
    ((178800460507842, 75598919458050, 325424424782523), (165, 126, 63)),
    ((284208542399126, 256132741265522, 321405650903323), (17, -8, 13)),
    ((223821213468504, 264316266103468, 281139093259777), (81, 90, -5)),
    ((240131429747674, 345630805399074, 240947290276915), (-44, 23, -26)),
    ((348682987321192, 208391140932624, 326995324886209), (-23, -7, 55)),
    ((278753794165100, 251751661883872, 292414590167205), (59, -63, 99)),
    ((259222149842530, 317941302002986, 416649704738535), (-29, 12, -417)),
    ((233904085506514, 314990781793458, 156242314955201), (-19, 150, 322)),
    ((221087785089460, 383274322511360, 252437492434365), (19, -119, -111)),
    ((461936521877078, 244277842402630, 548621897844300), (-112, -69, -141)),
    ((230249447084772, 335706407461184, 324051421621677), (55, -47, -145)),
    ((288195564995172, 249358581829384, 339168716842789), (-18, 50, -61)),
    ((227862788405394, 387521914619399, 391981087533441), (12, -152, -612)),
    ((223601096957678, 352311668817798, 215658475802885), (-7, 50, 44)),
    ((202789660293252, 343758898770596, 244768399901577), (110, 26, -37)),
    ((200192605133697, 351291390702014, 171647639581024), (111, 67, 271)),
    ((307603068372228, 371109045545672, 230917054453797), (14, -185, 158)),
    ((205108686659460, 274905031160408, 101368154978133), (126, 31, 374)),
    ((299449617901302, 165350551627409, 386915407799754), (-44, 212, -157)),
    ((219125988605232, 375028121211800, 220739173715865), (-35, -5, -57)),
    ((223219421336532, 5842030978400, 337076302428093), (95, 499, -65)),
    ((275211970027542, 388620443861111, 282739971942141), (-20, -190, 8)),
    ((329868363786648, 182353224435656, 383339889787111), (18, -11, 22)),
    ((291791583233012, 296308978470704, 430717976801389), (10, -71, -136)),
    ((310973968748912, 66801410788034, 390414567275349), (-20, 262, -84)),
    ((347978008922616, 292338032296076, 322894003871053), (-123, -26, -32)),
    ((309797592252837, 206351817231410, 372820734627753), (25, -13, 12)),
    ((317909011946064, 273726439069936, 350124519166037), (-27, -39, -22)),
    ((183628648873724, 208750976532488, 91141125791781), (193, 694, 639)),
    ((327730209755700, 120575508655780, 216949405888957), (-66, 231, 160)),
    ((244279683006969, 237232743990806, 330520241179647), (51, 99, -70)),
    ((155831907559278, 65510505675176, 86391346202031), (193, 169, 327)),
    ((243483553933104, 332175308606316, 292911093341279), (-12, 11, -139)),
    ((292847568294552, 258887243242824, 266358845806349), (-87, 123, 16)),
    ((239247569783437, 295404748814374, 357417636759299), (49, 15, -166)),
    ((275657961453765, 253691637127880, 343024148789040), (59, -59, 39)),
    ((252941792404842, 302678812553684, 155169186140339), (-27, 79, 295)),
    ((359397727668962, 310619543976464, 379160712807589), (-100, -83, -80)),
    ((390199109291514, 156762432955203, 236206813985950), (-72, 54, 155)),
    ((282031716749058, 151489471627269, 222953191918035), (-72, 408, 114)),
    ((534482701391061, 378310387104134, 557482958254026), (-213, -197, -183)),
    ((226983524057440, 408859254253844, 202826596658529), (-71, -242, 81)),
    ((372725459533224, 143209235232044, 403582082762745), (-31, 38, -7)),
    ((300899856163966, 159893804918046, 393980244994621), (44, 15, 8)),
    ((320862011031673, 558858538944333, 366383159937963), (-229, -678, -330)),
    ((251653043249657, 248356448642039, 354716776502574), (53, 34, -69)),
    ((259870213268174, 204973293150217, 351149002124417), (28, 140, -92)),
    ((216242060520676, 203805338031269, 310932612313502), (86, 328, -137)),
    ((329440856046252, 483184066069904, 422714199960549), (11, -305, -29)),
    ((213522113613397, 373489228592728, 228648610847661), (65, -91, 24)),
    ((323115200120868, 235587723802088, 295954349794173), (22, -59, 104)),
    ((283143341726837, 389876306481524, 198928777066454), (-175, -167, 155)),
    ((249251054666070, 298007487906368, 275680916666895), (17, 29, -5)),
    ((405969052832232, 194433424763234, 109288931935959), (-64, -14, 289)),
    ((346199032966456, 460764795062108, 370301221345873), (-113, -320, -107)),
    ((386610627945237, 200873113045259, 418455049711559), (-45, -20, -22)),
    ((220774699637424, 272967209696738, 350781671237393), (108, -26, -38)),
    ((313680566152662, 204738663013424, 349933235866524), (-32, 77, -37)),
    ((190148483038952, 345460982876474, 235329209749608), (170, 223, -177)),
    ((163563776119485, 288196475893162, 216453877254969), (201, -21, 156)),
    ((176765415680804, 160685457504320, 316986251462157), (194, 445, -153)),
    ((184693564992242, 378263695613774, 364859315066139), (163, -182, -75)),
    ((193182172263315, 281528651173448, 301577089555974), (149, 165, -159)),
    ((303745909260726, 192559333437604, 493233449594669), (36, -7, -104)),
    ((294244853697277, 283401849980267, 266550987543852), (-55, 22, 45)),
    ((300814558147092, 424610562386624, 357687063287229), (-89, -267, -173)),
    ((304590094809114, 197572832185211, 422679371024428), (-12, 76, -133)),
    ((244399540938504, 228832075415024, 267729613286490), (34, 173, 23)),
    ((124892084699766, 233167654961440, 6637976664097), (219, -51, 394)),
    ((404761450708164, 392843084787284, 547325612776749), (-82, -212, -184)),
    ((412733120685028, 116542630667696, 419860346893637), (-82, 79, -35)),
    ((301111909325343, 235385844186842, 316302907913166), (-41, 75, -21)),
    ((186570217463588, 190220161864624, 150518701509229), (166, 323, 296)),
    ((242087891589038, 274748409887806, 366973251871808), (88, -61, -17)),
    ((317563630874397, 180290625038159, 454532620787424), (-17, 76, -149)),
    ((422077468355314, 277287264857774, 356048826994043), (-116, -77, 14)),
    ((132278185562628, 202113069957216, 207139104779533), (321, 350, 145)),
    ((258889957561237, 342006022629574, 230138711264074), (17, -97, 119)),
    ((260349508856802, 166521541331669, 381259341179544), (44, 150, -97)),
    ((188232273885525, 363982458148653, 239981451205810), (171, -34, -47)),
    ((207587445892074, 358064701936901, 281163812892627), (44, 122, -498)),
    ((230282705029972, 363755073776968, 194023200283837), (-80, 36, 142)),
    ((283751929361304, 359779102923536, 253638869482629), (-91, -103, 26)),
    ((241551353306478, 512658480526814, 145571861873774), (-84, -737, 391)),
    ((266124968270247, 205843249074434, 277408817565924), (31, 99, 61)),
    ((304353660638068, 351278759340154, 390067282126063), (-82, -112, -217)),
    ((219583667246388, 395283855127536, 275925467424125), (-9, -160, -343)),
    ((200238629572305, 372635842423838, 175662151800555), (73, 127, 282)),
    ((265657030164461, 249360384604012, 237384470300627), (37, 17, 130)),
    ((213715265873508, 367489084236800, 231291412124397), (-31, 112, -212)),
    ((211932584456748, 364198275024722, 202595599075296), (14, 75, 69)),
    ((367074867587952, 336247486021964, 412228484276789), (-21, -158, -10)),
    ((381130941021598, 292289346567647, 214319952225984), (-112, -68, 171)),
    ((243982968254708, 161415145494048, 237414911748429), (67, 171, 126)),
    ((384743653926522, 161526892300382, 388624327004529), (-45, 22, 6)),
    ((321594905813232, 160522914803608, 431580262999760), (-30, 117, -133)),
    ((296625065844916, 204295805694384, 396981884731545), (-86, 233, -273)),
    ((255548780447427, 461116678187870, 245406899744943), (65, -301, 130)),
    ((305591035233588, 197100311229347, 300476166440784), (-26, 100, 31)),
    ((321019883946128, 245961601523616, 341793669275931), (-16, -19, 9)),
    ((284187024048852, 399265669529264, 449616532888749), (-239, -196, -924)),
    ((395094262293591, 245138371402714, 439282502506742), (-63, -57, -55)),
    ((312615700860072, 308564126854484, 388103257403805), (-15, -92, -68)),
    ((196859249797563, 391749723900482, 215907327094116), (103, -53, -183)),
    ((206152511877787, 292051357783024, 252110274906014), (85, 333, -127)),
    ((424938339526022, 129062322204399, 377734186566467), (-80, 48, 22)),
    ((187343643692620, 287181759306897, 244769502039958), (158, -54, 125)),
    ((440110571597447, 399361941761979, 315353820330899), (-91, -220, 86)),
    ((309631584673442, 329042296651574, 127733127977574), (-76, -77, 321)),
    ((363185760350946, 506023799164412, 186505736012103), (-76, -359, 210)),
    ((257976924960278, 205745942804816, 230000682652677), (-29, 325, 84)),
    ((299856853808572, 92869488650632, 179374299722389), (-55, 374, 221)),
    ((215562913636844, 372493914209876, 209494896435278), (-34, 49, -7)),
    ((402628567012692, 215963940624644, 438434781590259), (-178, 71, -185)),
    ((360100908534306, 89805422809949, 345843012673431), (-48, 148, 22)),
    ((209111308071346, 331429458428516, 226732630213341), (89, 55, 48)),
    ((288212853653532, 259498238064704, 300883697766489), (-16, 29, 9)),
    ((261265910446392, 293880205266704, 161865472184649), (48, -59, 246)),
    ((313056986780718, 230746028033897, 493701282718194), (-64, 86, -339)),
    ((244066614030516, 313783694427824, 265838672602557), (12, 23, -10)),
    ((213907729691428, 372674950937242, 219798186347659), (22, -21, -14)),
    ((227439777520792, 271638286530184, 204657027793549), (28, 249, 136)),
    ((145395808514476, 91759266369680, 86424430799091), (226, 277, 369)),
    ((370970955493392, 51270370998084, 455270844282149), (-39, 150, -74)),
    ((379316490881172, 433829238121184, 282789916285429), (-96, -263, 83)),
    ((174308434270558, 242278644067593, 201084452544934), (184, 74, 181)),
    ((206522493524004, 383114014313528, 204844149507237), (6, 20, -25)),
    ((417277346623935, 104250447374809, 339791067624967), (-69, 68, 62)),
    ((326688863994918, 64860998422064, 456278937908023), (9, 133, -73)),
    ((263712614365335, 327447026622147, 251708663944851), (-39, -16, 30)),
    ((208941145409496, 390100104157829, 187830404485725), (18, -92, 168)),
    ((297872607957957, 154706814205409, 364124012651889), (37, 44, 20)),
    ((366090864911172, 555099196039920, 369909607520845), (-180, -510, -142)),
    ((306163246750201, 216902239866313, 237119188728226), (-37, 86, 125)),
    ((348830006702280, 190378956585028, 268860115435036), (-78, 89, 89)),
    ((218910124652588, 375825082393761, 218364578791972), (-34, -10, -41)),
    ((427666942639420, 259791095561569, 216887150196961), (-146, -42, 172)),
    ((200157577101687, 334642244630999, 194905266519819), (115, 118, 154)),
    ((208100950281708, 370988781583000, 208716694715149), (36, 39, 16)),
    ((227359369893372, 58132659717908, 170772662981322), (110, 177, 229)),
    ((219168850175690, 324561890481878, 246343214764101), (47, 94, -36)),
    ((240310346285868, 227817835064840, 217138996818405), (-43, 500, 73)),
    ((275316762804617, 154223272520329, 13357181188829), (17, 180, 488)),
    ((228523652467752, 286684847899759, 141965011496443), (-30, 378, 418)),
    ((209436325864183, 388321700377814, 203589628044018), (-41, -17, -33)),
    ((460142114924362, 225533221534074, 430776611689699), (-197, 8, -107)),
    ((304163769654169, 276589169276377, 211208056572550), (-29, -18, 169)),
    ((275719799365752, 221497121281118, 308109969550671), (60, -26, 79)),
    ((248682681015810, 404116467554798, 471597722328785), (81, -224, -142)),
    ((193420131151228, 368119088819720, 134162339652600), (142, 325, 903)),
    ((224601060990628, 322239857419344, 262320215765277), (86, -57, 54)),
    ((203082746935140, 205347192707804, 124499269264629), (134, 88, 306)),
    ((238028454581300, 299991700723384, 287581403635597), (8, 108, -116)),
    ((287148348482385, 65593270561158, 318173476097710), (51, 131, 73)),
    ((369628394326452, 474293103197024, 158579456384589), (-137, -337, 254)),
    ((260085590554519, 393714709437399, 345164831257995), (19, -202, -102)),
    ((251266853525092, 202989687925904, 304803227439229), (82, 10, 73)),
    ((195666220960884, 343067946375231, 217196133267666), (102, 860, -387)),
    ((459444801147029, 432119488127526, 415119788725248), (-121, -253, -21)),
    ((352861340193658, 200831577896822, 371795290156375), (-11, -20, 25)),
    ((178030821917888, 230095209187632, 194697415504341), (227, 689, 150)),
    ((256945389408765, 298830878749703, 293786768793867), (9, 10, -30)),
    ((236853938536791, 454620646512424, 344004223658711), (-33, -427, -458)),
    ((259540268934660, 319392196048358, 273302415864093), (-218, 254, -288)),
    ((229076947960772, 130866677277686, 331818045619291), (70, 382, -115)),
    ((190561747488188, 36000353103960, 263687490514201), (154, 417, 74)),
    ((401754508765674, 458076042614960, 562037146082727), (-77, -283, -197)),
]

from scipy.optimize import fsolve
def equations(p):
    x, y, z, xv, yv, zv = p
    res = []
    for i in data[1:4]:
        ((x1,y1,z1),(xv1,yv1,zv1)) = i
        res.append((x-x1)*(yv-yv1) - (y-y1)*(xv-xv1))
        res.append((x-x1)*(zv-zv1) - (z-z1)*(xv-xv1))
    return res
x, y, z, xv, yv, zv = fsolve(equations, data[0][0] + data[0][1])
print(int(x+y+z))