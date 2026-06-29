import { useEffect, useMemo, useState } from 'react'
import { api } from '../api/client'
import { useNavigate } from 'react-router-dom'
import EnrollCourses from '../components/EnrollCourses'

export default function Dashboard() {
  const navigate = useNavigate()
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  const [onlineCount, setOnlineCount] = useState(0)
  const [profile, setProfile] = useState(null)
  const [enrolled, setEnrolled] = useState([])

  const refresh = async () => {
    setError('')
    const [profileRes, userCountRes, coursesRes] = await Promise.all([
      api.profile(),
      api.userCount(),
      api.getCourses(),
    ])

    setProfile(profileRes?.data || null)
    setOnlineCount(userCountRes?.count ?? 0)
    setEnrolled(coursesRes?.data ?? [])
  }

  useEffect(() => {
    let active = true
    ;(async () => {
      try {
        setLoading(true)
        await refresh()
      } catch (err) {
        if (!active) return
        setError(err?.message || 'Failed to load dashboard')
      } finally {
        if (!active) return
        setLoading(false)
      }
    })()

    return () => {
      active = false
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  const handleLogout = async () => {
    await api.logout().catch(() => {})
    navigate('/login', { replace: true })
  }

  const availableCourses = useMemo(
    () => [
      { id: 1, course_name: 'DBMS' },
      { id: 2, course_name: 'OS' },
      { id: 3, course_name: 'CN' },
      { id: 4, course_name: 'DSA' },
      // Example from your other endpoint response:
      { id: 3, course_name: 'Java' },
      { id: 4, course_name: 'SQL' },
    ],
    []
  )

  return (
    <div className="portal">
      <div className="dash-top">
        <div>
          <h1>Dashboard</h1>
          <p className="subtle">Welcome {profile?.name ? profile.name : '...'}</p>
        </div>
        <button className="btn btn-ghost" onClick={handleLogout} type="button">
          Logout
        </button>
      </div>

      {loading && <div className="card">Loading...</div>}
      {!loading && error && <div className="alert alert-error">{error}</div>}

      {!loading && !error && (
        <div className="grid">
          <div className="card">
            <h2>Online Users</h2>
            <div className="stat">{onlineCount}</div>
          </div>

          <div className="card">
            <h2>My Profile</h2>
            <div className="kv">
              <div className="row">
                <span>Name</span>
                <strong>{profile?.name ?? '-'}</strong>
              </div>
              <div className="row">
                <span>Phone</span>
                <strong>{profile?.phone ?? '-'}</strong>
              </div>
              <div className="row">
                <span>Department ID</span>
                <strong>{profile?.department_id ?? '-'}</strong>
              </div>
            </div>
          </div>

          <div className="card" style={{ gridColumn: '1 / -1' }}>
            <h2>Enrolled Courses</h2>
            {enrolled.length === 0 ? (
              <p className="muted">No courses enrolled yet.</p>
            ) : (
              <ul className="list">
                {enrolled.map((c) => (
                  <li key={c.id}>
                    <strong>{c.course_name}</strong> <span className="muted">(ID: {c.id})</span>
                  </li>
                ))}
              </ul>
            )}
          </div>

          <div className="card" style={{ gridColumn: '1 / -1' }}>
            <h2>Enroll Courses</h2>
            <EnrollCourses
              availableCourses={availableCourses}
              alreadyEnrolled={enrolled}
              onEnrolled={async () => {
                await refresh()
              }}
            />
          </div>
        </div>
      )}
    </div>
  )
}

