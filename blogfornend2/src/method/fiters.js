export function ToDateFormat (date) {
  const d = new Date(date)
  return (d.getMonth() + 1) + '/' + d.getDate() + '  ' + d.getHours() + ':' + d.getMinutes()
}

export function ToDateFormat2 (date) {
  const d = new Date(date)
  const Year = d.getFullYear()
  let Month = (d.getMonth() + 1)
  let Day = d.getDate()
  if ((d.getMonth() + 1) < 10) {
    Month = '0' + (d.getMonth() + 1)
  }
  if (d.getDate() < 10) {
    Day = '0' + d.getDate()
  }
  return Year + '-' + Month + '-' + Day
}

export function getCookie (cname) {
  const name = cname + '='
  const decodedCookie = decodeURIComponent(document.cookie)
  const ca = decodedCookie.split(';')
  for (let i = 0; i < ca.length; i++) {
    let c = ca[i]
    while (c.charAt(0) === ' ') {
      c = c.substring(1)
    }
    if (c.indexOf(name) === 0) {
      return c.substring(name.length, c.length)
    }
  }
  return ''
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
