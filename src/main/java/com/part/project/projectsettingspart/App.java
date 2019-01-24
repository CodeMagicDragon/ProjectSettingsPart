package com.part.project.projectsettingspart;

import android.app.Activity;
import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;

import com.part.project.projectsettingspart.model.AppDatabase;
import com.part.project.projectsettingspart.model.Card;

public class App extends Application
{
    public static App instance;

    private AppDatabase appDatabase;

    int baseCardNum;

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
        appDatabase = Room.databaseBuilder(this, AppDatabase.class, "cards_database").allowMainThreadQueries().build();
        String[] setNames = new String[]{"base"};
        String[][] firstTexts = new String[][]{{
                "be about", "break down", "break in",
                "break into", "break off", "break out",
                "break through", "bring about", "bring around",
                "bring down", "bring forward", "bring in",
                "bring up", "buy out", "call at",
                "call away", "call back", "call for",
                "call in", "call off", "call on",
                "carry forward", "carry on", "carry out",
                "catch on", "catch out", "catch up with",
                "check in", "check out", "clear up",
                "climb up", "close down", "come across",
                "come back", "come by", "come down",
                "come for", "come into", "come off",
                "come over to", "come round/around", "come together",
                "cut down (on)", "cut out", "do away with",
                "do something about something", "do up", "do without",
                "draw back (from)", "draw in", "draw up",
                "drive off", "drop in (on smb.)", "fall down",
                "fall off", "fall out", "fall over",
                "fill in/out", "find out", "fix up",
                "get (a)round", "get across", "get ahead",
                "get at", "get away", "get back",
                "get by", "get down", "get down to",
                "get into", "get off", "get on with",
                "get over", "get round to", "get through",
                "get up", "give away", "give back",
                "give in", "give over to", "give up",
                "go away", "go off", "go on",
                "go out", "hand in", "hand out",
                "hold on", "hold up", "hurry up",
                "keep down", "keep up", "keep up with",
                "knock down", "leave out", "let in",
                "level off", "look/have a look at", "look after",
                "look back", "look down on", "look for",
                "look forward to", "look in/into", "look out",
                "look out of", "look over", "look through",
                "look up", "look up to", "make at",
                "make for", "make out", "make up",
                "pay back", "pick up", "point out",
                "pull out", "pull aside", "put away",
                "put back", "put down", "put forward",
                "put in", "put off", "put on",
                "put out", "put through", "put together",
                "put up", "put up with", "ring back",
                "ring up", "run away (from)", "run off",
                "run out (of)", "run up", "save up (for)",
                "see ahead", "see off", "see to",
                "sell out", "send out", "set about",
                "set aside", "set off", "set up",
                "show off", "show up", "slow down",
                "sort out", "spell out", "squeeze through",
                "stand out", "step in", "sum up",
                "take after", "take apart", "take away",
                "take back", "take down", "take for",
                "take in", "take off", "take on",
                "take out", "take over", "take up",
                "talk over", "think over", "think up",
                "throw away", "try on", "try out",
                "turn away", "turn down", "turn on/off",
                "turn out", "turn over", "turn round",
                "turn up", "wash up", "watch out",
                "weigh up", "wind up", "work out",
                "write down", "write off"
        }};
        String[][] secondTexts = new String[][]{{
                "собираться, намереваться что-л. сделать", "сломаться; потерпеть неудачу", "приучать (к чему-л.), дисциплинировать",
                "вторгаться, завоевывать (рынок); начинать тратить (деньги)", "прекращать (переговоры), разрывать (отношения)", "разразиться, внезапно начать делать",
                "прорваться, добиться успеха", "осуществлять, вызывать", "убедить кого-л., изменить мнение",
                "снижать (цену)", "выдвигать (предложение); перенести на более ранний срок", "вносить (на рассмотрение)",
                "воспитывать; поднимать (вопрос)", "выкупать", "заходить куда-л.",
                "вызывать (по делу)", "отзывать, возвращать", "требовать; заходить за к-л.",
                "приглашать (домой)", "отменить", "навещать, посещать кого-л.",
                "переносить (что-л. на другую дату)", "продолжать", "выполнять",
                "войти в моду; завоевать популярность", "завалить (на экзамене)", "догонять, приближаться к какому-л. уровню",
                "регистрироваться", "освободить номер в гостинице; расплатиться на кассе", "выяснять, узнавать",
                "влезать, взбираться", "закрывать", "случайно встретить",
                "возвращаться", "заходить, заглядывать; проходить мимо", "снижаться; успокаиваться",
                "заходить за кем-л.", "получить, унаследовать", "состояться; удаваться; проходить с успехом",
                "переходить (на чью-то сторону), менять мнение", "заходить ненадолго", "объединиться",
                "сокращать", "вырезать; вычеркнуть, исключить", "избавиться от чего-л., отменить что-л.",
                "делать, поделать", "упаковывать", "обходиться без",
                "выходить (из игры, дела, предприятия)", "уменьшать (расходы), экономить", "составлять (контракт)",
                "уехать", "заходить (в гости)", "упасть, потерпеть неудачу",
                "уменьшаться, ухудшаться", "ссориться", "споткнуться, упасть",
                "заполнять (анкету)", "выяснить, узнать", "привети в порядок; организовать",
                "распространяться, становиться известным", "четко изложить", "преуспевать, продвигаться",
                "добраться, достать", "выходить; удирать", "возмещать; вернуться",
                "сводить концы с концами; проходить, проезжать", "записывать; нервировать", "приняться за что-либо (кого-либо)",
                "войти; попадать в какое-либо положение, состояние", "сойти, слезть", "ладить",
                "выздороветь", "находить время, приступать к чему-л.", "справиться с чем-либо; дозвониться",
                "вставать", "выдавать, разоблачать", "возвращать, отдавать",
                "уступать; подавать (отчет)", "отдаваться, посвящать себя целиком (чему-либо)", "отказаться, бросить",
                "уходить, исчезать, проходить", "выстреливать, взрываться", "продолжать",
                "выходить, бывать в обществе", "подавать; возвращать", "выдавать, раздавать",
                "ждать (у телефона)", "останавливать, задерживать", "торопить(ся)",
                "задерживать рост, мешать развитию", "продолжать, поддерживать", "быть наравне, успевать",
                "сломать, разрушить, сбить", "пропускать", "впускать; допускать",
                "выравнивать", "смотреть на", "присматривать, ухаживать за (кем-л.)",
                "вспоминать, оглядываться в прошлое", "смотреть свысока", "искать",
                "ждать с нетерпением", "заглянуть, заходить, изучать", "оглянуться, осмотреться, наблюдать",
                "выглядывать, смотреть из (окна)", "тщательно изучать", "просматривать",
                "смотреть вверх; искать в справочнике", "смотреть почтительно", "атаковать, наброситься",
                "направляться; способствовать, содействовать", "понять, разобраться, объяснить", "составлять; мириться; краситься",
                "отплатить, отомстить", "поднимать; заезжать, заходить (за кем-л.)", "указывать, обращать внимание",
                "выходить из предприятия, отказываться от участия", "откладывать (в сторону)", "убирать",
                "ставить на место", "поставить; записывать", "выдвигать (гипотезу)",
                "подавать (заявление, жалобу)", "откладывать", "надевать",
                "тушить; устранять", "соединять (по телефону)", "соединить, сложить",
                "вкладывать; повышать цену", "терпеть, мириться с ч-л.", "перезвонить",
                "позвонить", "убежать (от)", "истекать (о сроке)",
                "кончаться", "быстро расти, увеличиваться", "откладывать, копить (деньги), делать сбережения",
                "предвидеть, заглядывать в будущее", "провожать", "проследить",
                "распродавать", "рассылать", "приниматься",
                "отменять, откладывать", "намереваться, отправляться (в путь)", "основывать, учреждать",
                "представлять в выгодном свете; хвастать", "показывать; разоблачать", "замедлять; сокращать",
                "классифицировать; решать (проблему)", "разъяснять", "с трудом преодолеть",
                "выделяться, выступать", "вмешиваться", "суммировать, обобщать, подводить итог",
                "быть похожим", "разбирать на части", "убирать; уносить",
                "извиняться; брать обратно", "записать под диктовку; сносить, разрушать", "принимать за кого-л.",
                "принимать гостя; обманывать; пропускать", "снимать, вычитать; взлетать", "принимать на службу, браться (за дело); иметь успех",
                "вынимать, удалять", "вступать во владение", "взяться за что-л., заняться чем-л.; обсуждать",
                "обсудить", "обдумывать, размышлять", "придумывать",
                "выбрасывать; тратить, растрачивать", "примерять (платье)", "испытывать, проверять",
                "отворачиваться", "отвергать (предложение); отказывать (кому-л.)", "включать/выключать",
                "оказываться", "переворачивать, перелистывать; обдумывать", "обернуться",
                "появиться, оказаться", "мыть посуду", "остерегаться, быть осторожным",
                "взвесить и решить", "заканчивать", "разработать, составить, решать (задачу), тренироваться",
                "записывать", "списывать, аннулировать"
        }};
        baseCardNum = firstTexts.length;
        SharedPreferences sp = getApplicationContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();
        Card[] emptyNameSetCards;
        if (!sp.contains("first_launch"))
        {
            Card card = new Card();
            for (int i = 0; i < setNames.length; i++)
            {
                for (int j = 0; j < firstTexts[i].length; j++)
                {
                    card.name = firstTexts[i][j] + "(base)";
                    card.firstText = firstTexts[i][j];
                    card.secondText = secondTexts[i][j];
                    card.setName = setNames[i];
                    appDatabase.getCardDao().insert(card);
                }
            }
            spEditor.putInt("first_launch", 0);
            spEditor.putBoolean("block_option_1", true); // show cards
            spEditor.apply();
        }
        emptyNameSetCards = appDatabase.getCardDao().getBySetName("");
        for (int i = 0; i < emptyNameSetCards.length; i++)
        {
            appDatabase.getCardDao().delete(emptyNameSetCards[i]);
        }
    }

    public void destroyActivityOnResume(Activity activity)
    {
        SharedPreferences sp  = getApplicationContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
        if (!sp.getBoolean("start_activity", true))
        {
            activity.finish();
        }
    }

    public static App getInstance()
    {
        return instance;
    }

    public AppDatabase getAppDatabase()
    {
        return appDatabase;
    }
}
