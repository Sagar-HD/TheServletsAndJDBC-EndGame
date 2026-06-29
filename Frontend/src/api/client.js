import axios from 'axios'

const BASE_URL = 'http://localhost:8080'

export async function fetchJson(path, { method = 'GET', body, headers } = {}) {
  const res = await axios.request({
    url: `${BASE_URL}${path}`,
    method,
    data: body,
    headers,
    // important for servlet session cookies
    withCredentials: true,
    validateStatus: (s) => s >= 200 && s < 300,
  })

  return res.data
}

export const api = {
  register: (payload) => fetchJson('/StudentPortal/register', { method: 'POST', body: payload }),
  login: (payload) => fetchJson('/StudentPortal/login', { method: 'POST', body: payload }),
  logout: () => fetchJson('/StudentPortal/logout', { method: 'GET' }),
  profile: () => fetchJson('/StudentPortal/profile', { method: 'GET' }),
  userCount: () => fetchJson('/StudentPortal/usercount', { method: 'GET' }),
  getCourses: () => fetchJson('/StudentPortal/course', { method: 'GET' }),
  enrollCourses: (payloadArray) => fetchJson('/StudentPortal/course', { method: 'POST', body: payloadArray }),
}


