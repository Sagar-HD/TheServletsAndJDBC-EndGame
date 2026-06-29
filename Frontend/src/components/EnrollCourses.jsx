import { useMemo, useState } from 'react'
import { api } from '../api/client'

export default function EnrollCourses({ availableCourses, alreadyEnrolled, onEnrolled }) {
  const alreadyIds = useMemo(() => new Set((alreadyEnrolled || []).map((c) => c.id)), [alreadyEnrolled])
  const [selected, setSelected] = useState(() => [])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  const [success, setSuccess] = useState('')

  const toggle = (course) => {
    setSelected((prev) => {
      const exists = prev.some((c) => c.id === course.id)
      if (exists) return prev.filter((c) => c.id !== course.id)
      return [...prev, course]
    })
  }

  const onSubmit = async (e) => {
    e.preventDefault()
    setError('')
    setSuccess('')
    setLoading(true)
    try {
      if (selected.length === 0) {
        setError('Select at least one course')
        return
      }

      // Backend example expects: [{ id, course_name }, ...]
      await api.enrollCourses(selected.map((c) => ({ id: c.id, course_name: c.course_name })))
      setSuccess('courses added successfully')
      setSelected([])
      await onEnrolled()
    } catch (err) {
      setError(err?.message || 'Enrollment failed')
    } finally {
      setLoading(false)
    }
  }

  const normalizedAvailable = useMemo(() => {
    // de-duplicate by id, preferring first occurrence
    const seen = new Set()
    const out = []
    for (const c of availableCourses || []) {
      if (seen.has(c.id)) continue
      seen.add(c.id)
      out.push(c)
    }
    return out
  }, [availableCourses])

  return (
    <form onSubmit={onSubmit}>
      {error && <div className="alert alert-error">{error}</div>}
      {success && <div className="alert alert-success">{success}</div>}

      <div className="course-grid">
        {normalizedAvailable.map((course) => {
          const isEnrolled = alreadyIds.has(course.id)
          const isChecked = selected.some((c) => c.id === course.id)
          return (
            <label key={course.id} className={`course ${isEnrolled ? 'course-disabled' : ''}`}>
              <input
                type="checkbox"
                checked={isChecked}
                disabled={isEnrolled}
                onChange={() => toggle(course)}
              />
              <div className="course-name">
                {course.course_name}
                <div className="muted">ID: {course.id}</div>
              </div>
            </label>
          )
        })}
      </div>

      <button className="btn" type="submit" disabled={loading}>
        {loading ? 'Enrolling...' : 'Enroll Selected'}
      </button>
      <p className="muted" style={{ marginTop: 8 }}>
        Courses already enrolled are disabled.
      </p>
    </form>
  )
}

