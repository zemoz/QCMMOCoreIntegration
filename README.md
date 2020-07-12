# QCMMOCoreIntegration

A simple plugin adding MMOCore conditions and objects to QuestCreator.

Tested only on 1.16 but should work on older mc versions.
Old MMOCore version won't work due to api changes.

### Conditions
_operation_ possible values : EQUALS, DIFFERENT, AT_LEAST, LESS_THAN
```yaml
type: MMOCORE_MAIN_LEVEL
operation: 'AT_LEAST'
value: '1'
```
```yaml
type: MMOCORE_PROFESSION_LEVEL
profession: 'mining'
operation: 'AT_LEAST'
value: '1'
```
### Server objects
```yaml
type: SERVER_MMOCORE_GIVE_MAIN_XP
value: '1'
```
```yaml
type: SERVER_MMOCORE_GIVE_PROFESSION_XP
profession: 'mining'
value: '1'
```