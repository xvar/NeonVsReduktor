# Neon Vs Reduktor Vs RxRedux

### Версия №2

Простой пример (counter) для сравнения 3-х redux-like framework'ов: 
[neon](https://github.com/technoir42/neon), 
[reduktor](https://github.com/g000sha256/reduktor/),
[rx redux](https://github.com/freeletics/RxRedux)

#### Заметки по первому впечатлению:

1. Action'ы в reduktor принимаются и извне, и могут быть порождены внутри системы для её нужд.
В Neon есть разделение Action'ов на Action + Effect (которые служат для триггера use-case'ов)

2. Mapper в reduktor имеет определённый интерфейс, neon никак не регламентирует маппинг

3. Listener в neon выглядит не слишком продуманно, как и явный subject для external-events.
Присутствует переключение потоков на computation. 
Не исключено, что я не понимаю замысел автора, т.к. авторского примера нет. ¯\_(ツ)_/¯

4. RxRedux фокусируется на `action`, `side effect` - это alias для
`fun effect(action : Observable<Action>, state: StateAccessor<State>) : Observable<Action>`
Также, как и reduktor позволяет останавливать effect в зависимости от другого. Нужно помнить про
`distinctUntilChanged` и про `Observable.empty`

5. RxRedux и Reduktor имеют `stateAccessor`, neon нет

##### Свои выводы в текущей версии не привожу, оставлю на момент обсуждения. :)
