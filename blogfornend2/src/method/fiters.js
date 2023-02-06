export function ToDateFormat (date) {
  const d = new Date(date)
  return (d.getMonth() + 1) + '/' + d.getDate() + '  ' + d.getHours() + ':' + d.getMinutes()
}

export function MonthString (num) {
  const data = {
    1: '一月',
    2: '二月',
    3: '三月',
    4: '四月',
    5: '五月',
    6: '六月',
    7: '七月',
    8: '八月',
    9: '九月',
    10: '十月',
    11: '十一月',
    12: '十二月'
  }
  return data[num]
}
