import { Navigate, Outlet, useLocation } from 'react-router-dom'
import { useEffect, useState } from 'react'
import { api } from '../api/client'

export default function RequireAuth() {
  const location = useLocation()
  const [status, setStatus] = useState('checking')

  useEffect(() => {
    let active = true
    ;(async () => {
      try {
        await api.profile()
        if (!active) return
        setStatus('authenticated')
      } catch {
        if (!active) return
        setStatus('unauthenticated')
      }
    })()
    return () => {
      active = false
    }
  }, [])

  if (status === 'checking') {
    return (
      <div style={{ padding: 24 }}>
        <h2>Loading...</h2>
      </div>
    )
  }

  if (status === 'unauthenticated') {
    return <Navigate to="/login" replace state={{ from: location }} />
  }

  return <Outlet />
}

